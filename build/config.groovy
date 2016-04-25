
withConfig(configuration) {
    inline(phase: 'CONVERSION') { source, context, classNode ->
        classNode.putNodeMetaData('projectVersion', '0.1.94')
        classNode.putNodeMetaData('projectName', 'ngplugin')
        classNode.putNodeMetaData('isPlugin', 'true')
    }
}
