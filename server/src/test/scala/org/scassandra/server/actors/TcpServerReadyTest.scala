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
package org.scassandra.server.actors

import akka.actor.ActorSystem
import akka.testkit._
import org.scalatest.{FunSpecLike, Matchers}
import org.scassandra.server.ServerReady
import org.scassandra.server.priming.ActivityLog
import org.scassandra.server.priming.prepared.PrimePreparedStore
import org.scassandra.server.priming.query.PrimeQueryStore

class TcpServerReadyTest extends TestKit(ActorSystem("TestSystem")) with FunSpecLike with Matchers {

  describe("ServerReady") {
    it("should be send to the actor that registers as a listener") {
      // given
      // TODO [DN] Do test probes shut down their own actor systems?
      val tcpReadyListener = TestProbe()

      // when
      TestActorRef(new TcpServer("localhost", 8046, PrimeQueryStore(), PrimePreparedStore(), tcpReadyListener.ref, new ActivityLog))

      // then
      tcpReadyListener.expectMsg(ServerReady)
    }

  }
}
