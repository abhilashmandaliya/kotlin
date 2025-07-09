/*
 * Copyright 2010-2025 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.js.test.klib

import org.jetbrains.kotlin.js.test.JsFailingTestSuppressor
import org.jetbrains.kotlin.js.test.fir.setupDefaultDirectivesForFirJsBoxTest
import org.jetbrains.kotlin.js.test.ir.AbstractJsBlackBoxCodegenTestBase.JsBackendFacades
import org.jetbrains.kotlin.js.test.ir.commonConfigurationForJsBackendSecondStageTest
import org.jetbrains.kotlin.js.test.ir.commonServicesConfigurationForJsCodegenTest
import org.jetbrains.kotlin.js.test.ir.configureJsBoxHandlers
import org.jetbrains.kotlin.test.FirParser
import org.jetbrains.kotlin.test.TargetBackend
import org.jetbrains.kotlin.test.backend.BlackBoxCodegenSuppressor
import org.jetbrains.kotlin.test.builders.TestConfigurationBuilder
import org.jetbrains.kotlin.test.model.FrontendKinds
import org.jetbrains.kotlin.test.runners.AbstractKotlinCompilerWithTargetBackendTest
import org.jetbrains.kotlin.test.services.configuration.JsEnvironmentConfigurator

open class AbstractCustomJsCompilerFirstPhaseTest : AbstractKotlinCompilerWithTargetBackendTest(TargetBackend.JS_IR) {
    override fun configure(builder: TestConfigurationBuilder) = with(builder) {
        commonServicesConfigurationForJsCodegenTest(/* Does not matter. */ FrontendKinds.FIR)
        facadeStep(::CustomWebCompilerFirstPhaseFacade)

        useAfterAnalysisCheckers(
            ::JsFailingTestSuppressor,
            ::BlackBoxCodegenSuppressor,
        )

        enableMetaInfoHandler()

        commonConfigurationForJsBackendSecondStageTest(
            pathToTestDir = "${JsEnvironmentConfigurator.TEST_DATA_DIR_PATH}/box/",
            testGroupOutputDirPrefix = "customJsCompilerFirstPhaseTest/",
            backendFacades = JsBackendFacades.WithRecompilation
        )

        setupDefaultDirectivesForFirJsBoxTest(/* Does not matter. */ FirParser.LightTree)

        configureJsBoxHandlers()
    }
}
