package sameparents

import org.junit.jupiter.api.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class SameParentsFinderTest {

    private val sameParentsFinder = SameParentsFinder()

    @Test
    fun `should return true when nodes have same parent`() {
        // given
        val firstNode = Node("first")
        val secondNode = Node("second")
        val parent = Node("parent")
        val connections = listOf(
            NodesConnection(child = firstNode, parent = parent),
            NodesConnection(child = secondNode, parent = parent)
        )

        // expect
        assertTrue(firstNode haveSameParentWith secondNode using connections)
    }

    @Test
    fun `should return false when nodes does not have same parent`() {
        // given
        val firstNode = Node("first")
        val secondNode = Node("second")
        val firstParent = Node("firstParent")
        val secondParent = Node("secondParent")
        val connections = listOf(
            NodesConnection(child = firstNode, parent = firstParent),
            NodesConnection(child = secondNode, parent = secondParent)
        )

        // expect
        assertFalse(firstNode haveSameParentWith secondNode using connections)
    }

    @Test
    fun `should return true when nodes have same parent in complex structure`() {
        val firstLevelFirstNode = Node("first-level-first-node")
        val firstLevelSecondNode = Node("first-level-second-node")
        val firstLevelThirdNode = Node("first-level-third-node")
        val secondLevelFirstNode = Node("second-level-first-node")
        val secondLevelSecondNode = Node("second-level-second-node")
        val thirdLevelFirstNode = Node("third-level-first-node")
        val thirdLevelSecondNode = Node("third-level-second-node")
        val thirdLevelThirdNode = Node("third-level-third-node")
        val connections = listOf(
            NodesConnection(child = secondLevelFirstNode, parent = firstLevelFirstNode),
            NodesConnection(child = secondLevelFirstNode, parent = firstLevelSecondNode),
            NodesConnection(child = secondLevelSecondNode, parent = firstLevelSecondNode),
            NodesConnection(child = secondLevelSecondNode, parent = firstLevelThirdNode),
            NodesConnection(child = thirdLevelFirstNode, parent = secondLevelFirstNode),
            NodesConnection(child = thirdLevelSecondNode, parent = secondLevelFirstNode),
            NodesConnection(child = thirdLevelSecondNode, parent = secondLevelSecondNode),
            NodesConnection(child = thirdLevelThirdNode, parent = secondLevelSecondNode),
        )

        assertTrue(secondLevelFirstNode haveSameParentWith secondLevelSecondNode using connections)
        assertTrue(secondLevelFirstNode haveSameParentWith thirdLevelSecondNode using connections)
        assertTrue(thirdLevelFirstNode haveSameParentWith thirdLevelThirdNode using connections)
    }

    private infix fun Node.haveSameParentWith(node: Node): SameParentChecker {
        return SameParentChecker(this, node)
    }

    inner class SameParentChecker(private val firstNode: Node, private val secondNode: Node) {
        infix fun using(connections: List<NodesConnection>): Boolean {
            return sameParentsFinder.haveSameParent(connections, firstNode, secondNode)
        }
    }
}
