package ngplugin



import grails.dev.commands.*
import grails.util.Holders
import org.codehaus.groovy.grails.scaffolding.*;

class CreateNgView2Command implements ApplicationCommand {

    boolean handle(ExecutionContext ctx) {
		def args = ctx.commandLine.remainingArgs
		def className = args[0]
		println className
		def grailsApp = grails.util.Holders.grailsApplication
		def domainClass = grailsApp.getDomainClass(className)
		
		def templateGeneratorClass = Holders.config.grails.plugin.scaffolding.customTemplateGenerator
		println "templateGeneratorClass -------- " + templateGeneratorClass
		if(!templateGeneratorClass){
			templateGeneratorClass = Holders.config.grails.plugin.scaffolding.DefaultGrailsTemplateGenerator
		}
		println "templateGeneratorClass 11 -------- " 
		
		if (! templateGeneratorClass) {
			templateGeneratorClass = grailsApp.classLoader.loadClass('org.grails.plugins.scaffolding.DefaultGrailsTemplateGenerator')
		}
		println "templateGeneratorClass 22 -------- " + templateGeneratorClass
		
        return true
    }
}
