package ngplugin


import org.apache.commons.lang.StringUtils;

import grails.dev.commands.*

class CreateAngularRouteCommand implements ApplicationCommand {

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
		File file = createFileForController('routes.js', className, packageName)
		writeToFile(file, className, packageName)
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
		def lowerCaseClassName = StringUtils.uncapitalize(className)
		def packages = packageName.replace('.', '/')
		println "packages -- " + packages
		file.write '//= wrapped\n\n'
		file << 'angular.module("'+packageName+'.'+lowerCaseClassName+'")\n'
		file << '	.config(function($routeProvider) {\n'
		file << '		$routeProvider.\n'
		file << '		when("/'+lowerCaseClassName+'", {\n'
		file << '			templateUrl: "/'+packages+'/'+lowerCaseClassName+'/'+lowerCaseClassName+'.html",\n'
		file << '			controller: "'+className+'ListController as ctrl"\n'
		file << '		}).\n'
		file << '		when("/'+lowerCaseClassName+'/create", {\n'
		file << '			templateUrl: "/'+packages+'/'+lowerCaseClassName+'/create.html",\n'
		file << '			controller: "'+className+'CreateController as ctrl"\n'
		file << '		}).\n'
		file << '		when("/'+lowerCaseClassName+'/edit/:'+lowerCaseClassName+'Id", {\n'
		file << '			templateUrl: "/'+packages+'/'+lowerCaseClassName+'/create.html",\n'
		file << '			controller: "'+className+'CreateController as ctrl"\n'
		file << '		}).\n'
		file << '		when("/'+lowerCaseClassName+'/:'+lowerCaseClassName+'Id", {\n'
		file << '			templateUrl: "/'+packages+'/'+lowerCaseClassName+'/show.html",\n'
		file << '			controller: "'+className+'ShowController as ctrl"\n'
		file << '		})\n'
		file << '	});\n'
	}
}
