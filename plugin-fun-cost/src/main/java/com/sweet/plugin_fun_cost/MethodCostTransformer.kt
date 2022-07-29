package com.sweet.plugin_fun_cost

import com.didiglobal.booster.transform.TransformContext
import com.didiglobal.booster.transform.asm.simpleName
import com.sweet.plugin_core.transformer.clazz.AbsClassTransformer
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.*

class MethodCostTransformer : AbsClassTransformer() {


    init {
        project.extensions.create("sweetCost",SweetCostConfig::class.java)
        project
    }



    fun coverPkgName(name: String): Boolean {
        return name.contains("com/sweet") || name.contains("com/white")
    }

    override fun transform(context: TransformContext, klass: ClassNode): ClassNode {
        if (onCommInterceptor(context, klass)) {
            return klass
        }
        if ((!coverPkgName(klass.name))
            || klass.name.contains("binding", true)
            || klass.name.contains("com/sweet/apm", true)
            || klass.simpleName.startsWith("R$")
            || klass.simpleName == "R"
            || klass.simpleName == "BuildConfig"
        ) {
            return klass
        }
        klass.methods.forEach { mtd ->
            println("kClassName " + klass.simpleName + " method " + mtd.name)
            val inns = mtd.instructions
            if (inns.size() == 0) {
                return@forEach
            }
            val itt = inns.iterator()
            val index = mtd.maxLocals + 2
            while (itt.hasNext()) {
                val inNode = itt.next()
                val op = inNode.opcode
                if ((op >= Opcodes.IRETURN && op <= Opcodes.RETURN) || op == Opcodes.ATHROW) {
                    val end = InsnList().also {
                        it.add(
                            MethodInsnNode(
                                Opcodes.INVOKESTATIC,
                                "java/lang/System",
                                "currentTimeMillis",
                                "()J",
                                false
                            )
                        )
                        it.add(VarInsnNode(Opcodes.LLOAD, index))
                        it.add(InsnNode(Opcodes.LSUB))
                        it.add(VarInsnNode(Opcodes.LSTORE, index))
                        it.add(LdcInsnNode(klass.name ?: "unKnown"))
                        it.add(LdcInsnNode(mtd.name ?: "unKnown"))
                        it.add(VarInsnNode(Opcodes.LLOAD, index))
                        it.add(
                            MethodInsnNode(
                                Opcodes.INVOKESTATIC,
                                "java/lang/Long",
                                "valueOf",
                                "(J)Ljava/lang/Long;",
                                false
                            )
                        )
                        it.add(
                            MethodInsnNode(
                                Opcodes.INVOKESTATIC,
                                "com/sweet/apm/asm/cost/CostPlugin",
                                "onMethodExit",
                                "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Long;)V",
                                false
                            )
                        )
                    }
                    inns.insert(inNode.previous, end)
                }
            }

            val il = InsnList()
            il.add(
                MethodInsnNode(
                    Opcodes.INVOKESTATIC,
                    "java/lang/System",
                    "currentTimeMillis",
                    "()J",
                    false
                )
            )

            il.add(VarInsnNode(Opcodes.LSTORE, index))

            mtd.instructions.insert(il)

        }

        return klass
    }


}