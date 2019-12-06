package com.sjtech.readwrite.integration

import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.integration.annotation.Transformer
import org.springframework.stereotype.Component
import java.io.File


@Component
class FileTransformer {

    @Autowired
    lateinit var log: Logger

    @Value("\${coindump.dir.archive}")
    private val archiveDir: String? = null

    @Transformer(inputChannel = "fileInputChannel", outputChannel = "fileToJobProcessor")
    @Throws(Exception::class)
    fun transform(aFile: File): File? {

        //Move old file to archive directory.
        val archivedFile = File(archiveDir + "/" + aFile.name)

        if (aFile.renameTo(archivedFile)) {
            log.info(String.format("%s file archived to %s", aFile.name, aFile.absolutePath))
        }
        return archivedFile
    }
}