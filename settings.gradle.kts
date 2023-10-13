pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        // https://repository.tnkad.net:8443/repository/public/
        maven(url = "https://repository.tnkad.net:8443/repository/public/")
        maven(url = "https://repository.tnkad.net:8443/repository/tnk_test/")
    }
}

rootProject.name = "payplus"
include(":app")
 