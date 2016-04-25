package ngplugin


import org.apache.commons.lang.StringUtils;

import grails.dev.commands.*

class CreateAngularModuleCommand implements ApplicationCommand {

    boolean handle(ExecutionContext ctx) {
		println "CreateAngularModuleCommand START"
		def args = ctx.commandLine.remainingArgs
		def grailsApp = grails.util.Holders.grailsApplication
		def domainClass = grailsApp.getDomainClass(args[0])
		if(!domainClass) {
			println "| Error: Domain class with name " + args[0] + " not found. Please use fully qualified name."
			return true
		}
		def className = domainClass.getName()
		def packageName = domainClass.getPackageName()
		def lowerCaseClassName = StringUtils.uncapitalize(className)
		String fileName = lowerCaseClassName + '.js'
		File file = createFileForController(fileName, className, packageName)
		writeToFile(file, className, packageName)
		if(file.exists()) {
			println '| File created at ' + file.getPath()
		}
        return true
    }
	
	def createFileForController(String fileName, String className, String packageName){
		def lowerCaseClassName = StringUtils.uncapitalize(className)
		String baseDir = System.getProperty("user.dir")
		String dirPath = baseDir + File.separator + 'grails-app' + File.separator + 'assets' + File.separator + 'javascripts' + File.separator
		def packages = packageName.split('[.]')
		packages?.each{pName ->
			dirPath += pName + File.separator
		}
		dirPath += lowerCaseClassName
		new File(dirPath).mkdirs()
		String filePath = dirPath + File.separator + fileName
		new File(filePath)
	}
	
	def writeToFile(File file, String className, String packageName) {
		println "writeToService START"
		def lowerCaseClassName = StringUtils.uncapitalize(className)
		file.write '//= wrapped\n'
		file << '//= require /angular/angular \n'
		file << '//= require_self\n'
		file << '//= require routes\n'
		file << '//= require_tree services\n'
		file << '//= require_tree controllers\n'
		file << '//= require_tree directives\n'
		file << '//= require_tree domain\n'
		file << '//= require_tree templates\n\n'
		file << 'angular.module("'+packageName+'.'+lowerCaseClassName+'", []);\n'
		
	}
}
