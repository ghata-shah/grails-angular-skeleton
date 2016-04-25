package ngplugin


import org.apache.commons.lang.StringUtils;
import grails.dev.commands.*

class CreateAngularServiceCommand implements ApplicationCommand {

    boolean handle(ExecutionContext ctx) {
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
		String fileName = lowerCaseClassName + 'DataFactoryService.js'
		File file = createFileForService(fileName, className, packageName)
		writeToService(file, className, packageName)
		if(file.exists()) {
			println '| File created at ' + file.getPath()
		}
		return true
    }
	
	def createFileForService(String fileName, String className, String packageName){
		def lowerCaseClassName = StringUtils.uncapitalize(className)
		String baseDir = System.getProperty("user.dir")
		String dirPath = baseDir + File.separator + 'grails-app' + File.separator + 'assets' + File.separator + 'javascripts' + File.separator
		def packages = packageName.split('[.]')
		packages?.each{pName ->
			dirPath += pName + File.separator
		}
		dirPath += lowerCaseClassName + File.separator + 'services'
		new File(dirPath).mkdirs()
		String filePath = dirPath + File.separator + fileName
		new File(filePath)
	}
	
	def writeToService(File file, String className, String packageName) {
		def lowerCaseClassName = StringUtils.uncapitalize(className)
		file.write '//= wrapped\n\n'
		file << 'angular\n'
		file << '	.module("'+packageName+'.'+lowerCaseClassName+'")\n'
		file << '    .factory("'+lowerCaseClassName+'DataFactoryService", '+lowerCaseClassName+'DataFactoryService);\n\n'
		file << 'function '+lowerCaseClassName+'DataFactoryService(DomainServiceFactory) {\n'
		file << '	return DomainServiceFactory("/'+lowerCaseClassName+'/:action/:'+lowerCaseClassName+'Id",{'+lowerCaseClassName+'Id:"@id",action:"@action"},\n'
        file << '		{"show": {method: "GET"}},\n'
        file << '		{"save": {method: "POST"}},\n'
        file << '		{"delete": {method: "DELETE"}}\n'
		file << '	);\n'
		file << '}\n'
	}
}
