package egger.software.spring

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper

interface Client {
    fun get(url: String): String
    fun post(url: String, body: String): String
    fun put(url: String, body: String): String
    fun delete(url: String, body: String)
}

class HttpClient : Client {
    override fun get(url: String): String {
        return """
            [
                { "id": 1, "title": "Buy eggs" },
                { "id": 2, "title": "Buy milk" }            
            ]
        """.trimIndent()
    }

    override fun post(url: String, body: String): String {
        return "dummy"
    }

    override fun put(url: String, body: String): String {
        return "dummy"
    }

    override fun delete(url: String, body: String) {
    }

}

data class Task(
    @JsonProperty("id")  val id: Int,
    @JsonProperty("title") val title: String
)

class TasksService {
    private val client = HttpClient()
    private val mapper = ObjectMapper()

    fun fetchTasks(): Array<Task> {
        val result = client.get("/tasks")
        return mapper.readValue(result, Array<Task>::class.java);
    }
}

class TasksServiceIoc(private val client: HttpClient) {
    val mapper = ObjectMapper()

    fun fetchTasks(): Array<Task> {
        val result = client.get("/tasks")
        return mapper.readValue(result, Array<Task>::class.java);
    }
}

fun main() {

    val service = TasksService()
    println(service.fetchTasks().contentToString())

    println("")

    val serviceIoc = TasksServiceIoc(HttpClient())
    println(serviceIoc.fetchTasks().contentToString())

}
