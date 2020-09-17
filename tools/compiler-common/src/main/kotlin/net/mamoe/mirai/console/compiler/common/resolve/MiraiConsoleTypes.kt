/*
 * Copyright 2020 Mamoe Technologies and contributors.
 *
 * 此源代码的使用受 GNU AFFERO GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU AGPLv3 license that can be found through the following link.
 *
 * https://github.com/mamoe/mirai/blob/master/LICENSE
 */

package net.mamoe.mirai.console.compiler.common.resolve

import net.mamoe.mirai.console.compiler.common.castOrNull
import net.mamoe.mirai.console.compiler.common.firstValue
import org.jetbrains.kotlin.descriptors.annotations.Annotated
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.resolve.constants.EnumValue

///////////////////////////////////////////////////////////////////////////
// Command
///////////////////////////////////////////////////////////////////////////

val COMPOSITE_COMMAND_SUB_COMMAND_FQ_NAME = FqName("net.mamoe.mirai.console.command.CompositeCommand.SubCommand")
val SIMPLE_COMMAND_HANDLER_COMMAND_FQ_NAME = FqName("net.mamoe.mirai.console.command.SimpleCommand.Handler")

///////////////////////////////////////////////////////////////////////////
// Plugin
///////////////////////////////////////////////////////////////////////////

val PLUGIN_FQ_NAME = FqName("net.mamoe.mirai.console.plugin.Plugin")
val JVM_PLUGIN_DESCRIPTION_FQ_NAME = FqName("net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription")
val SIMPLE_JVM_PLUGIN_DESCRIPTION_FQ_NAME = FqName("net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription")

///////////////////////////////////////////////////////////////////////////
// Resolve
///////////////////////////////////////////////////////////////////////////

val RESOLVE_CONTEXT_FQ_NAME = FqName("net.mamoe.mirai.console.compiler.common.ResolveContext")

/**
 * net.mamoe.mirai.console.compiler.common.ResolveContext.Kind
 */
enum class ResolveContextKind {
    PLUGIN_ID,
    PLUGIN_NAME,
    PLUGIN_VERSION,

    ;

    companion object {
        fun valueOfOrNull(string: String): ResolveContextKind? = ResolveContextKind.values().find { it.name == string }
    }
}

fun Annotated.isResolveContext(kind: ResolveContextKind) = this.resolveContextKind == kind

val Annotated.resolveContextKind: ResolveContextKind?
    get() {
        val ann = this.findAnnotation(RESOLVE_CONTEXT_FQ_NAME) ?: return null
        val (_, enumEntryName) = ann.allValueArguments.firstValue().castOrNull<EnumValue>()?.value ?: return null // undetermined kind
        return ResolveContextKind.valueOf(enumEntryName.asString())
    }