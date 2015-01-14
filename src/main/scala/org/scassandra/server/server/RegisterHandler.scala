/*
 * Copyright (C) 2014 Christopher Batey and Dogan Narinc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.scassandra.server

import akka.actor.{Actor, ActorRef}
import com.typesafe.scalalogging.slf4j.Logging
import akka.io.Tcp.Write
import akka.util.ByteString
import org.scassandra.server.cqlmessages.CqlMessageFactory

class RegisterHandler(connection: ActorRef, msgFactory: CqlMessageFactory) extends Actor with Logging {
  def receive = {
    case registerMsg @ RegisterHandlerMessages.Register(_, stream) => {
      logger.debug(s"Received register message $registerMsg")
      connection ! msgFactory.createReadyMessage(stream)
    }
    case msg @ _ => {
      logger.info(s"Received unknown message $msg")
    }
  }
}

object RegisterHandlerMessages {
  case class Register(messageBody: ByteString, stream: Byte)

}