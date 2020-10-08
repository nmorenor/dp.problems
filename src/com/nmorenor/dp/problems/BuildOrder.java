package com.nmorenor.dp.problems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * You are given a list of projects and a list of dependencies (which is a list of pairs of projects,
 * where the second project is dependent on the first project). All of a project's dependencies must
 * be valid before the project is. Find a build order that will allow the projects to be built. 
 * If there is no valid build order, return an error.
 * 
 * EXAMPLE
 * input:
 *   projects: a, b, c, d, e, f
 *   dependencies: (a, d), (f, b), (b, d), (f, a), (d, c)
 * 
 * @author nacho
 *
 */
public class BuildOrder {
	
	private static class Project {
		
		private final String name;
		
		public Project(String name) {
			this.name = name;
		}
		
		@Override
			public String toString() {
				return name;
			}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Project other = (Project) obj;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			return true;
		}
	}
	
	private static class Graph {
		
		Map<Project, List<Project>> adj = new HashMap<Project, List<Project>>();
		Map<String, Project> nodes = new HashMap<String, Project>();
		
		public Graph(String[] projects) {
			for (String next : projects) {
				Project project = new Project(next);
				adj.put(project, new ArrayList<Project>());
				nodes.put(next, project);
			}
		}
		
		public Project getProject(String name) {
			return nodes.get(name);
		}
		
		public void addEdge(String projectA, String projectB) {
			if (projectA == null || projectB == null || !nodes.containsKey(projectA) || !nodes.containsKey(projectB)) {
				return;
			}
			
			adj.get(nodes.get(projectB)).add(nodes.get(projectA));
		}
	}

	public static void main(String[] args) throws Exception {
		String projects = "a, b, c, d, e, f";
		String dependencies = "(a, d), (f, b), (b, d), (f, a), (d, c)";
		
		Scanner scanner = new Scanner(projects);
		scanner.useDelimiter(",");
		List<String> projectList = new ArrayList<String>();
		while (scanner.hasNext()) {
			projectList.add(scanner.next().trim());
		}
		scanner.close();
		
		scanner = new Scanner(dependencies);
		scanner.useDelimiter(Pattern.compile("\\)"));
		
		Graph graph = new Graph(projectList.toArray(new String[projectList.size()]));
		
		while (scanner.hasNext()) {
			String next = scanner.next().trim();
			if (next.startsWith("(")) {
				next = next.substring(1);
			}
			if (next.startsWith(",")) {
				next = next.substring(3);
			}
			String projectA = next.substring(0, next.indexOf(","));
			String projectB = next.substring(next.indexOf(",") + 2);
			graph.addEdge(projectA, projectB);
		}
		scanner.close();
		
		List<Project> order = new ArrayList<Project>();
		Set<Project> marked = new HashSet<Project>();
		Set<Project> onStack = new HashSet<Project>();
		for (String next : projectList) {
			Project nextProject = graph.getProject(next);
			if (marked.contains(nextProject)) {
				continue;
			}
			dfs(graph, nextProject, marked, onStack, order);
		}
		System.out.println(order);
	}
	
	private static void dfs(Graph graph, Project project, Set<Project> marked, Set<Project> onStack, List<Project> order) throws Exception {
		marked.add(project);
		onStack.add(project);
		for (Project nextProject : graph.adj.get(project)) {
			if (!marked.contains(nextProject)) {
				dfs(graph, nextProject, marked, onStack, order);
			} else if (onStack.contains(nextProject)) {
				throw new Exception("Cycle found");
			}
		}
		onStack.remove(project);
		order.add(project);
	}
}
