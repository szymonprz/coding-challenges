package sameparents

class SameParentsFinder {

    fun haveSameParent(nodes: List<NodesConnection>, firstNode: Node, secondNode: Node): Boolean {
        val allNodes = nodes.flatMap { setOf(it.child, it.parent) }.toSet()
        if (!allNodes.contains(firstNode)) return false
        if (!allNodes.contains(secondNode)) return false
        val nodeWithDirectParents: Map<Node, List<Node>> = nodes.groupBy { pair -> pair.child }
            .mapValues { it.value.map { it.parent } }
        val allParentsOfFirstNode = getAllParentsOfNode(nodeWithDirectParents, firstNode)
        val allParentsOfSecondNode = getAllParentsOfNode(nodeWithDirectParents, secondNode)
        return allParentsOfFirstNode.intersect(allParentsOfSecondNode).isNotEmpty()
    }

    private fun getAllParentsOfNode(nodeWithDirectParents: Map<Node, List<Node>>, firstNode: Node): Set<Node> {
        val allParents = mutableSetOf<Node>()
        val nextParents = nodeWithDirectParents[firstNode].orEmpty()
        for (parent in nextParents) {
            allParents.add(parent)
            allParents.addAll(getAllParentsOfNode(nodeWithDirectParents, parent))
        }
        return allParents
    }
}
