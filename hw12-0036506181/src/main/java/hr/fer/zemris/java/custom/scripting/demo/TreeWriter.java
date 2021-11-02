package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;

/**
 * Program that prints the tree traversal of the parsed document body of the
 * file given with path.
 * 
 * @author Frano Rajič
 */
public class TreeWriter {

	/**
	 * Entry point for program
	 * 
	 * @param args should contain only one argument - the file path of a smart
	 *             script
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("One argument should be given - the file name!");
			return;
		}

		try {
			Path filePath = Paths.get(args[0]);
			String docBody = Files.readString(filePath);

			System.out.println(docBody);
			System.out.println();
			System.out.println();

			SmartScriptParser p = new SmartScriptParser(docBody);
			WriterVisitor visitor = new WriterVisitor();
			p.getDocumentNode().accept(visitor);
		} catch (InvalidPathException e) {
			System.out.println("Invalid path given..");
			return;
		} catch (IOException e) {
			System.out.println("Couldn't read given file...");
		}
	}

	/**
	 * This help class models an {@link INodeVisitor} that goes through the parsed
	 * document body tree and writes an tree of its elements.
	 * 
	 * @author Frano Rajič
	 */
	private static class WriterVisitor implements INodeVisitor {

		@Override
		public void visitTextNode(TextNode node) {
			System.out.print(node.getText());
			goDeeper(node);
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			System.out.print(String.format("{$ FOR %s %s %s %s$}", node.getVariable().getName(),
					node.getStartExpression().asText(), node.getEndExpression().asText(),
					(node.getStepExpression() == null ? "" : node.getStepExpression().asText() + " ")));
			goDeeper(node);
			System.out.print("{$END$}");
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			System.out.print(
					"{$=" + node.getElements().stream().map(Element::asText).reduce("", (x, y) -> x + " " + y) + " $}");
			goDeeper(node);
		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			goDeeper(node);
		}

		/**
		 * Help method that goes through all children of a node and calls the visitor
		 * onto them
		 * 
		 * @param node the node to dig into
		 */
		private void goDeeper(Node node) {
			for (int i = 0; i < node.numberOfChildren(); i++) {
				node.getChild(i).accept(this);
			}
		}

	}
}
