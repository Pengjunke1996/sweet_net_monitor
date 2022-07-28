package com.sweet.monitor_plugin.asm.transformer.clazz.net_monitor

import com.didiglobal.booster.transform.TransformContext
import com.didiglobal.booster.transform.asm.simpleName
import com.sweet.monitor_plugin.asm.transformer.clazz.AbsClassTransformer
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.*

class NetMonitorTransform : AbsClassTransformer() {

    override fun transform(context: TransformContext, klass: ClassNode): ClassNode {
        if (onCommInterceptor(context, klass)){
            return klass
        }
        if (klass.name != "okhttp3/OkHttpClient\$Builder"){
            return klass
        }
        klass.methods.forEach { mtd->
            if (mtd.name == "build"){
                println("NetMonitorTransform " + klass.simpleName + " method " + mtd.name)
                val insList =  InsnList().also {
                    it.add(VarInsnNode(Opcodes.ALOAD,0))
                    it.add(FieldInsnNode(Opcodes.GETFIELD, "okhttp3/OkHttpClient\$Builder", "interceptors", "Ljava/util/List;"))
                    it.add(FieldInsnNode(Opcodes.GETSTATIC, "com/sweet/net_monitor/MonitorHelper", "INSTANCE", "Lcom/sweet/net_monitor/MonitorHelper;"))
                    it.add(MethodInsnNode(Opcodes.INVOKEVIRTUAL,"com/sweet/net_monitor/MonitorHelper", "getHookInterceptors", "()Ljava/util/List;", false))
                    it.add(MethodInsnNode(Opcodes.INVOKEINTERFACE, "java/util/List", "addAll", "(Ljava/util/Collection;)Z", true))
                    it.add(InsnNode(Opcodes.POP))
                }
                mtd.instructions.insert(insList)
            }
        }
        return super.transform(context, klass)
    }
}