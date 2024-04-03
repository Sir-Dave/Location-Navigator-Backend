package com.sirdave.locationnavigator.service

import com.cloudinary.Cloudinary
import com.cloudinary.utils.ObjectUtils
import com.google.common.io.Files
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile

@Component
class CloudinaryService {

    @Value("\${cloudinary.name}")
    private val cloudinaryName: String? = null

    @Value("\${cloudinary.apiKey}")
    private val apiKey: String? = null

    @Value("\${cloudinary.apiSecret}")
    private val apiSecret: String? = null

    private val log = LoggerFactory.getLogger(this.javaClass)

    private fun getConfiguration(): Cloudinary {

        return Cloudinary(
            ObjectUtils.asMap(
                "cloud_name", cloudinaryName,
                "api_key", apiKey,
                "api_secret", apiSecret,
                "secure", "true"
            )
        )
    }

    @Suppress("UnstableApiUsage")
    fun uploadMultipleFiles(files: List<MultipartFile>, folder: String): List<String>{
        val cloudinary = getConfiguration()
        val imageUrls = mutableListOf<String>()
        files.forEach { file ->
            val fileName = file.originalFilename?.let { Files.getNameWithoutExtension(it) }
            val data = cloudinary.uploader().upload(
                file.bytes,
                ObjectUtils.asMap(
                    "folder", "location-navigator/$folder/",
                    "use_filename", "true",
                    "unique_filename", "false",
                    "public_id", fileName
                )
            ) as HashMap<*, *>
            log.info("cloudinary data is $data")
            imageUrls.add(data["secure_url"] as String)
        }
        return imageUrls
    }

    fun deleteFromCloudinary(publicId: String?){
        val response = getConfiguration().uploader().destroy(publicId,
            ObjectUtils.emptyMap()) as HashMap<Any?, Any?>
        println("delete response is ==========> $response")
    }
}