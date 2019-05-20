

public class list<T>{

	public abstract class iterator{
		public T data;

		public abstract iterator prev();
		public abstract iterator next();
	}


	private class Node extends iterator{
		private Node prev;
		private Node next;

		public Node(){}

		public Node(T t) {
			data = t;
		}

		public Node prev() {
			return prev;
		}

		public Node next() {
			return next;
		}
	};


	private Node head;
	private Node tail;
	private int size;


	public list() {
		init();
	}

	private void init() {
		head = new Node();
		tail = new Node();

		head.prev = null;
		head.next = tail;

		tail.prev = head;
		tail.next = null;

		size = 0;
	}


	public T front() {
		return head.next.data;
	}


	public T back() {
		return tail.prev.data;
	}


	public iterator begin() {
		return head.next;
	}


	public iterator end() {
		return tail;
	}


	public iterator rbegin() {
		return tail.prev;
	}


	public iterator rend() {
		return head;
	}


	public boolean empty() {
		return size == 0;
	}


	public int size() {
		return size;
	}


	public list<T> clear() {
		init();
		return this;
	}


	public list<T> insert(iterator it, T t) throws Exception {
		Node node, prevNode, nextNode;
		nextNode = (Node)it;
		prevNode = nextNode.prev;
		if (prevNode == null)
			throw new Exception(iteratorError);
		node = new Node(t);
		prevNode.next = node;
		node.prev = prevNode;
		node.next = nextNode;
		nextNode.prev = node;
		return this;
	}


	public list<T> erase(iterator it) throws Exception {
		Node node, prevNode, nextNode;
		node = (Node)it;
		prevNode = node.prev;
		nextNode = node.next;
		if (prevNode == null || nextNode == null)
			throw new Exception(iteratorError);
		prevNode.next = nextNode;
		nextNode.prev = prevNode;
		return this;
	}


	public list<T> push_back(T t) throws Exception {
		return insert(end(), t);
	}


	public list<T> pop_back() throws Exception {
		return erase(rbegin());
	}


	public list<T> push_front(T t) throws Exception {
		return insert(begin(), t);
	}


	public list<T> pop_front() throws Exception {
		return erase(begin());
	}


	public list<T> reverse() {
		Node node, nodeTmp;
		nodeTmp = head;
		head = tail;
		tail = nodeTmp;

		node = head;
		while (node != tail) {
			nodeTmp = node.prev;
			node.prev = node.next;
			node.next = nodeTmp;
			node = node.next;
		}

		nodeTmp = node.prev;
		node.prev = node.next;
		node.next = nodeTmp;
		return this;
	}


	static final String iteratorError = "24k list>>> iterator out of bound";

}
