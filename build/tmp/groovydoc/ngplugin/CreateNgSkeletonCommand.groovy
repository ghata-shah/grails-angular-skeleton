package ngplugin


import grails.dev.commands.*

class CreateNgSkeletonCommand implements ApplicationCommand {

    boolean handle(ExecutionContext ctx) {
		println "VIEW................"
		CreateNgViewCommand viewCommand = new CreateNgViewCommand()
		viewCommand.handle(ctx)
		
		println "CONTROLLER.........."
		CreateAngularControllerCommand ctrlCommand = new CreateAngularControllerCommand()
		ctrlCommand.handle(ctx)
		
		println "SERVICE............"
		CreateAngularServiceCommand serviceCommand = new CreateAngularServiceCommand()
		serviceCommand.handle(ctx)
		
		println "MODULE.............."
		CreateAngularModuleCommand moduleCommand = new CreateAngularModuleCommand()
		moduleCommand.handle(ctx)
		
		println "ROUTE.............."
		CreateAngularRouteCommand routeCommand = new CreateAngularRouteCommand()
		routeCommand.handle(ctx)
        return true
    }
}
