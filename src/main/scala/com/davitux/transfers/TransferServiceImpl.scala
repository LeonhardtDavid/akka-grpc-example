package com.davitux.transfers
import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.scaladsl.{Sink, Source}
import com.google.protobuf.empty.Empty

import scala.concurrent.Future

class TransferServiceImpl(implicit system: ActorSystem) extends TransferService {

  import system.dispatcher

  override def execute(in: TransferRequest): Future[TransferResponse] = {
    Future.successful {
      TransferResponse(
        request = Some(in),
        status = TransferResponse.TransferStatus.APPROVE,
        info = Nil
      )
    }
  }

  override def executeAll(in: Source[TransferRequest, NotUsed]): Future[TransferResponse] =
    in.runWith(Sink.seq).map(_ => TransferResponse(status = TransferResponse.TransferStatus.APPROVE))

  override def history(in: Empty): Source[TransferResponse, NotUsed] =
    Source(List(
      TransferResponse(status = TransferResponse.TransferStatus.APPROVE),
      TransferResponse(status = TransferResponse.TransferStatus.REJECTED)
    ))

  override def executeEach(in: Source[TransferRequest, NotUsed]): Source[TransferResponse, NotUsed] =
    in.map { req =>
      TransferResponse(
        request = Some(req),
        status = TransferResponse.TransferStatus.APPROVE,
        info = Nil
      )
    }

}
