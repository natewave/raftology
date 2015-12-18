package com.zengularity.raft

import akka.actor.ActorSystem

object ApplicationMain extends App {
  val system = ActorSystem("Raftology")
  val nodeActor = system.actorOf(NodeActor.props, "nodeActor")
  system.awaitTermination()
}
