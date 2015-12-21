package com.zengularity.raftology

import akka.actor._
import akka.cluster.ClusterEvent._

class SimpleClusterMonitor extends Actor with ActorLogging {

  def receive = {
    case state: CurrentClusterState ⇒
      log.info("Current nodes: {}", state.members)

    case MemberUp(member) ⇒
      log.info("Node is Up: {}", member)

    case UnreachableMember(member) ⇒
      log.info("Node detected as unreachable: {}", member)

    case MemberRemoved(member, previousStatus) ⇒
      log.info(s"Node is Removed: $member, previous status: $previousStatus")

    case _: ClusterDomainEvent ⇒ // ignore
  }
}
