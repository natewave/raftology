package com.zengularity.raftology

import akka.actor._
import akka.cluster.Cluster
import akka.cluster.ClusterEvent._

object App {
  def main(args: Array[String]): Unit = {

    // Override the configuration of the port
    // when specified as program argument
    if (args.nonEmpty) System.setProperty("akka.remote.netty.tcp.port", args(0))

    // Create an Akka system
    val raftologySystem = ActorSystem("Raftology")
    val node = raftologySystem.actorOf(Props[Node], name = "node")
    Cluster(raftologySystem).subscribe(node, classOf[ClusterDomainEvent])

    // Monitor cluster
    //val clusterMonitor = raftologySystem.actorOf(Props[SimpleClusterMonitor], name = "clusterMonitor")
    //Cluster(raftologySystem).subscribe(clusterMonitor, classOf[ClusterDomainEvent])
  }
}
