plugins {
    base
}

val testAggregateReport = tasks.register<TestReport>("testAggregateReport") {
    group = "Reporting"
    description = "Collect aggregate test reports of all sub-modules."
    destinationDir = file("$buildDir/reports/tests")
    reportOn(subprojects.map {
        it.tasks.withType<AbstractTestTask>()
    })
}

subprojects {
    afterEvaluate {
        tasks.withType<AbstractTestTask> {
            finalizedBy(testAggregateReport)
        }
    }
}
