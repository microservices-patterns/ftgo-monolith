import org.gradle.api.Plugin
import org.gradle.api.Project

class FtgoServicePlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {

        project.apply(plugin: 'org.springframework.boot')
    	project.apply(plugin: "io.spring.dependency-management")
    }
}
