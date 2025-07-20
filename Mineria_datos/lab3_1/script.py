import networkx as nx
G = nx.karate_club_graph()
pr = nx.pagerank(G)  # runs using backend from NETWORKX_BACKEND_PRIORITY, if set
