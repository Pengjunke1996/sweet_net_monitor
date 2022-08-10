package com.sweet.plugin_fun_cost

import com.didiglobal.booster.transform.TransformContext
import com.didiglobal.booster.transform.asm.simpleName
import com.google.auto.service.AutoService
import com.sweet.plugin_core.transformer.clazz.AbsClassTransformer
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Type
import org.objectweb.asm.tree.*

@AutoService(AbsClassTransformer::class)
class MethodCostTransformer : AbsClassTransformer() {

    private fun coverPkgName(name: String, simpleName: String): Boolean {
        if (Config.isNeedTraceClass(name, simpleName)) {
            return Config.isConfigTraceClass(name)
        }
        return false
    }


    override fun onPreTransform(context: TransformContext) {
        super.onPreTransform(context)
        initConfig()
    }

    fun initConfig() {
        val sweetCost = project.extensions.getByName("sweetCost") as SweetCostConfig
        Config.open = sweetCost.open
        Config.mTraceConfigFile = sweetCost.traceConfigFile
        Config.logTraceInfo = sweetCost.logTraceInfo

        Config.parseTraceConfigFile()
        print("MethodCostTransformer:$sweetCost")
    }

    override fun transform(context: TransformContext, klass: ClassNode): ClassNode {
        if (onCommInterceptor(context, klass)) {
            return klass
        }
        if (!coverPkgName(klass.name, klass.simpleName)) {
            return klass
        }
        klass.methods.forEach { mtd ->
            if (mtd.name == "onMethodCost") {
                return@forEach
            }
            val inns = mtd.instructions
            if (inns.size() == 0) {
                return@forEach
            }
            val itt = inns.iterator()
            val index = mtd.maxLocals + getSize(Type.LONG_TYPE)
            if (Config.logTraceInfo) {
                println("MethodCostTransformer ---" + klass.name + " method " + mtd.name + " index " + index + "---")
            }
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
                                "com/sweet/plugin/cost/CostPlugin",
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

            mtd.visitMaxs(mtd.maxStack,index)

        }

        return klass
    }

    private fun getSize(type: Type): Int {
        return when (type.sort) {
            Type.VOID -> 0
            Type.BOOLEAN, Type.CHAR, Type.BYTE, Type.SHORT, Type.INT, Type.FLOAT, Type.ARRAY, Type.OBJECT, 12 -> 1
            Type.LONG, Type.DOUBLE -> 2
            else -> throw AssertionError()
        }
    }


}