/*
 * Copyright 2010-2025 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.fir.extensions

import org.jetbrains.kotlin.fir.FirSession
import kotlin.reflect.KClass

abstract class FirScriptResolutionHacksExtension(
    session: FirSession,
) : FirExtension(session) {
    companion object {
        val NAME: FirExtensionPointName = FirExtensionPointName("ScriptResolutionHacksExtension")
    }

    final override val name: FirExtensionPointName
        get() = NAME

    final override val extensionType: KClass<out FirExtension> = FirScriptResolutionHacksExtension::class

    fun interface Factory : FirExtension.Factory<FirScriptResolutionHacksExtension>

    abstract val skipTowerDataCleanupForTopLevelInitializers: Boolean
}

val FirExtensionService.scriptResolutionHacksExtension: List<FirScriptResolutionHacksExtension> by FirExtensionService.registeredExtensions()
