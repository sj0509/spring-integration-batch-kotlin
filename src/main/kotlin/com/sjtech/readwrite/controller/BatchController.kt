package com.sjtech.readwrite.controller

import com.sjtech.readwrite.service.FileService
import org.slf4j.Logger
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/batch")
class BatchController(val log: Logger,
                      val fileService: FileService) {

    @PostMapping("/processCSVFile", consumes = ["multipart/form-data"])
    fun processCSVFile(@RequestParam("file") file: MultipartFile): String {
        log.info("processCSVFile called for '${file.originalFilename}'")

        if (file.isEmpty) {
            log.error("File is Empty!")
        } else {
            fileService.initiateBatchJob(file.originalFilename!!, file.bytes)
        }

        return "File Received !"
    }

}