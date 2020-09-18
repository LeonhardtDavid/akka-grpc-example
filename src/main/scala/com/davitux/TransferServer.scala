package com.davitux

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import com.davitux.transfers.{TransferServiceHandler, TransferServiceImpl}

import scala.concurrent.{ExecutionContext, Future}

class TransferServer(system: ActorSystem) {

  def run(): Future[Http.ServerBinding] = {
    implicit val sys: ActorSystem = system
    implicit val ec: ExecutionContext = sys.dispatcher

    val service: HttpRequest => Future[HttpResponse] = TransferServiceHandler(new TransferServiceImpl())

    val binding = Http()
      .newServerAt(
        interface = "127.0.0.1",
        port = 8080
      ).bind(service)

    // report successful binding
    binding.foreach { binding => println(s"gRPC server bound to: ${binding.localAddress}") }

    binding
  }

}

object TransferServer extends App {

  private val system = ActorSystem()

  new TransferServer(system).run()

}
