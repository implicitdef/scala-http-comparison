package study


import scala.concurrent.Future


object Main {

  def main(args: Array[String]): Unit = {
    import scala.concurrent.duration.Duration
    import scala.concurrent.Await
    Await.result(callWithPlayWs(), Duration.Inf)
    Await.result(callWithDispatch(), Duration.Inf)
    Await.result(callWithSprayClient(), Duration.Inf)
  }

  private def callWithPlayWs(): Future[Unit] = {
    import play.api.libs.ws.ning.NingWSClient
    import scala.concurrent.ExecutionContext.Implicits.global
    val wsClient = NingWSClient()
    wsClient
      .url("http://jsonplaceholder.typicode.com/comments/1")
      .withQueryString("some_parameter" -> "some_value", "some_other_parameter" -> "some_other_value")
      .withHeaders("Cache-Control" -> "no-cache")
      .get()
      .map { wsResponse =>
        if (! (200 to 299).contains(wsResponse.status)) {
          sys.error(s"Received unexpected status ${wsResponse.status} : ${wsResponse.body}")
        }
        println(s"OK, received ${wsResponse.body}")
        println(s"The response header Content-Length was ${wsResponse.header("Content-Length")}")
        wsClient.close()
      }
  }


  private def callWithDispatch(): Future[Unit] = {
    import dispatch._
    import dispatch.Defaults._
    val h = new Http
    val requestWithHandler =
      url("http://jsonplaceholder.typicode.com/comments/1")
        .<<?(Seq("some_parameter" -> "some_value", "some_other_parameter" -> "some_other_value"))
        .<:<(Seq("Cache-Control" -> "no-cache"))
        .OK { response =>
      println(s"OK, received ${response.getResponseBody}")
      println(s"The response header Content-Length was ${response.getHeader("Content-Length")}")
      h.shutdown()
    }
    h(requestWithHandler)
  }

  private def callWithSprayClient(): Future[Unit] = {
    import akka.actor._
    import spray.http._
    import spray.client.pipelining._
    implicit val actorSystem = ActorSystem()
    import actorSystem.dispatcher
    val pipeline = sendReceive
    pipeline(
      Get(
        Uri(
          "http://jsonplaceholder.typicode.com/comments/1"
        ).withQuery("some_parameter" -> "some_value", "some_other_parameter" -> "some_other_value")
      )
        .withHeaders(HttpHeaders.`Cache-Control`(CacheDirectives.`no-cache`))
    )
      .map { response =>
        if (response.status.isFailure) {
          sys.error(s"Received unexpected status ${response.status} : ${response.entity.asString(HttpCharsets.`UTF-8`)}")
        }
        println(s"OK, received ${response.entity.asString(HttpCharsets.`UTF-8`)}")
        println(s"The response header Content-Length was ${response.header[HttpHeaders.`Content-Length`]}")
        actorSystem.shutdown()
      }
  }






}
