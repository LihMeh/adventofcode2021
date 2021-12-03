package common

fun readResource(filename: String): String {
    val resource = object {}.javaClass.getResource(filename)
    checkNotNull(resource) { "Failed to read resource $filename" }
    return resource.readText(Charsets.UTF_8)
}