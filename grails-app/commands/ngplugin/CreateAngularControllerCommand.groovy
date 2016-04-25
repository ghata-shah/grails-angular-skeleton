package ngplugin


import org.apache.commons.lang.StringUtils;
import grails.dev.commands.*

class CreateAngularControllerCommand implements ApplicationCommand {

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
		String fileName = lowerCaseClassName + 'Controller.js'	
		File file = createFileForController(fileName, className, packageName)
		writeToController(file, className, packageName)
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
		dirPath += lowerCaseClassName + File.separator + 'controllers'
		new File(dirPath).mkdirs()
		String filePath = dirPath + File.separator + fileName
		new File(filePath)
	}
	
	def writeToController(File file, String className, String packageName) {
		def lowerCaseClassName = StringUtils.uncapitalize(className)
		file.write '//= wrapped\n\n'
		file << 'angular\n'
		file << '	.module("'+packageName+'.'+lowerCaseClassName+'")\n'
		file << '   .controller("'+className+'ListController", ["'+lowerCaseClassName+'DataFactoryService",'+className+'ListController])\n'
		file << '	.controller("'+className+'ShowController", ["'+lowerCaseClassName+'DataFactoryService","$routeParams","$location",'+className+'ShowController])\n'
		file << '	.controller("'+className+'CreateController", ["'+lowerCaseClassName+'DataFactoryService","$routeParams","$location",'+className+'CreateController])\n'
		generateListController(file, className)
		generateShowController(file, className)
		generateCreateController(file, className)
	}
	
	def generateListController(File file, String className) {
		def lowerCaseClassName = StringUtils.uncapitalize(className)
		file << 'function '+className+'ListController('+lowerCaseClassName+'Service) {\n'
		file << '	var self = this;\n'
		file << '	'+lowerCaseClassName+'Service.list(function(items){\n'
		file << '		self.'+lowerCaseClassName+'s = items;\n'
		file << '	});\n'
		file << '}\n'
	}
	
	def generateShowController(File file, String className) {
		def lowerCaseClassName = StringUtils.uncapitalize(className)
		file << 'function '+className+'ShowController('+lowerCaseClassName+'Service,$routeParams,$location){\n'
		file << '	var self = this;\n'
		file << '	'+lowerCaseClassName+'Service.show({'+lowerCaseClassName+'Id:$routeParams.'+lowerCaseClassName+'Id,action:"show"},function('+lowerCaseClassName+'){\n'
		file << '		self.'+lowerCaseClassName+' = '+lowerCaseClassName+';\n'
		file << '	});\n'
		file << '	self.delete = function('+lowerCaseClassName+'){\n'
		file << '		if(confirm("Are you sure you want to delete this '+className+'?")) {\n'
		file << '			'+lowerCaseClassName+'Service.delete({action: "delete",'+lowerCaseClassName+'Id:$routeParams.'+lowerCaseClassName+'Id}, '+lowerCaseClassName+', function (res) {\n'
		file << '			$location.path("/'+lowerCaseClassName+'/");\n'
		file << '			});\n'
		file << '		}\n'
		file << '	}\n'
		file << '}\n'
	}
	
	def generateCreateController(File file, String className) {
		def lowerCaseClassName = StringUtils.uncapitalize(className)
		file << 'function '+className+'CreateController('+lowerCaseClassName+'Service,$routeParams,$location){\n'
		file << '	var self = this;\n'
		file << '	if($routeParams.'+lowerCaseClassName+'Id){\n'
        file << '	'+lowerCaseClassName+'Service.show({'+lowerCaseClassName+'Id:$routeParams.'+lowerCaseClassName+'Id,action:"show"},function('+lowerCaseClassName+'){\n'
		file << '	   self.'+lowerCaseClassName+' = '+lowerCaseClassName+';\n'
		file << '		});\n'
		file << '	}\n'
		file << '	self.save = function('+lowerCaseClassName+'){\n'
		file << '	'+lowerCaseClassName+'Service.save({action:"save"},'+lowerCaseClassName+',function(res){\n'
        file << '	    if(res.id){\n'
        file << '	        $location.path("/'+lowerCaseClassName+'/"+res.id);\n'
        file << '	    }else{\n'
        file << '	       alert("Unknown error occured");\n'
		file << '		}\n'
		file << '	});\n'
		file << '	}\n'
		file << '}\n'
	}
}
