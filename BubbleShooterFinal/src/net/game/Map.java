package net.game;

public class Map {
static int [][]Maps;
static void initMaps(){
	Maps = new int[4][];
	int []map0={0,0,1,1,2,2,3,3,0,0,1,1,2,2,3,2,2,3,3};
	int []map1={0,0,1,1,2,2,3,3,0,0,1,1,2,2,3,2,2,3,3,0,0,1,1,2,3,3,0,0,1,1};
	int []map2={2,2,2,2,2,2,2,2,
			    1,1,1,1,1,1,1,1,
			    5,5,5,5,5,5,5,5,
			    4,4,4
			    };
	Maps[0]=map0;
	Maps[1]=map1;
	Maps[2]=map2;
}
}
