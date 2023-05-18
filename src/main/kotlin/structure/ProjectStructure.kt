package structure

import java.io.File
import java.io.FileReader

/**
 * Represents a literal folder. It may contain FireFiles and FireDirectories.
 */
class FireDirectory(name: ResourceName) : FireContainerResource<FireContainerResource<*>>(name)

/**
 * Represents a literal file. It may contain FireStructs, FireEnums, FireTraits, FireFunctions, etc.
 */
class FireFile(private val file: File, name: ResourceName) : FireContainerResource<FireContainerResource<*>>(name) {
    /**
     * The contents of the FireFile.
     *
     * TODO May fail / lag for large files due to .readLines().
     */
    val text: String
        get() = FileReader(file).readText()
}