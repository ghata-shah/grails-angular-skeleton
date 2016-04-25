package ngplugin


import grails.dev.commands.*

class CreateNgSkeletonCommand implements ApplicationCommand {

    boolean handle(ExecutionContext ctx) {
		CreateNgViewCommand viewCommand = new CreateNgViewCommand()
		viewCommand.handle(ctx)
		
		CreateAngularControllerCommand ctrlCommand = new CreateAngularControllerCommand()
		ctrlCommand.handle(ctx)
		
		CreateAngularServiceCommand serviceCommand = new CreateAngularServiceCommand()
		serviceCommand.handle(ctx)
		
		CreateAngularModuleCommand moduleCommand = new CreateAngularModuleCommand()
		moduleCommand.handle(ctx)
		
		CreateAngularRouteCommand routeCommand = new CreateAngularRouteCommand()
		routeCommand.handle(ctx)
        return true
    }
}
