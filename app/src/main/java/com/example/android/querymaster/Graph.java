package com.example.android.querymaster;

import java.util.*;


class Graph {
    private Map<String, Destination> MapOfVertices;
    private Map<String, List<Route>> graph;
    private String prevTime = "0";
    private String prevDate = "";
    private Map<String, Boolean> isVisited = new TreeMap<>();
    private List<List<Flight>> ListOfSeriesOfFlights = new ArrayList<>();
    private List<Flight> SeriesOfFlights = new ArrayList<>();
    private Map<String, Boolean> isVisitedFlight = new TreeMap<>();

    public Graph() {
        MapOfVertices = new TreeMap<>();
        graph = new TreeMap<>();
    }

    public void traverseGraph(String Date, Flight flight, String From, String To) {
        if (From.compareTo(To) == 0) {
            isVisited.put(From, false);
            isVisitedFlight.put(flight.getCode(), false);
            List<Flight> temp = new ArrayList<>();
            for (Flight f:SeriesOfFlights) {
                temp.add(f);
            }
            ListOfSeriesOfFlights.add(temp);
            return;
        }
        if (graph.containsKey(From)) {
            for (Route e : graph.get(From)) {
                if (!isVisited.get(e.getTo())) {
                    for (Flight f : e.getListOfFlights()) {
                        if (!isVisitedFlight.get(f.getCode())) {
                            isVisitedFlight.put(f.getCode(), true);

                            if (Integer.parseInt(f.getStartTime()) > Integer.parseInt(prevTime) && f.getDateOfJourney().compareTo(Date) == 0 && f.getSeatAvailable() > 0) {
                                String preTime = prevTime;
                                String preDate = prevDate;
                                prevTime = f.getEndTime();
                                prevDate = f.getDateOfJourney();

                                SeriesOfFlights.add(f);
                                traverseGraph(Date, f, e.getTo(), To);
                                SeriesOfFlights.remove(f);
                                prevDate = preDate;
                                prevTime = preTime;
                            }
                        }
                    }
                }
            }
        }
        isVisited.put(From, false);
    }

    public List<List<Flight>> getIndirectFlights(String Date, String From, String To) {    // mum,   kol
        ListOfSeriesOfFlights = new ArrayList<>();
        if (graph.containsKey(From)) {
            for (Route e : graph.get(From)) {
                SeriesOfFlights = new ArrayList<>();
                setVisited();
                prevTime = "0";
                prevDate = Date;
                traverseGraph(prevDate, null, From, To);
            }
        }
        return ListOfSeriesOfFlights;
    }

    public ArrayList<Flight> getModifiedListOfFlights(List<List<Flight>> ListOfSeriesFlights) {
        Set<List<Flight>> SetOfIndirectFlights = new HashSet<>(ListOfSeriesFlights);
        ListOfSeriesFlights = new ArrayList<>();
        ListOfSeriesFlights.addAll(SetOfIndirectFlights);
        ArrayList<Flight> ModifiedListOfFlights = new ArrayList<>();
        for (List<Flight> lf:ListOfSeriesFlights) {
            if (lf != null && lf.size() > 1) {
                String from = "";
                String ModifiedAirline = "";
                String ModifiedDuration = "0";
                String ModifiedCode="";
                int TotalCost = 0;
                String to = lf.get(lf.size() - 1).getTo();
                for (int i = 0; i < lf.size(); i++) {
                    from += lf.get(i).getFrom();
                    ModifiedAirline += lf.get(i).getAirlineName();
                    ModifiedCode+=lf.get(i).getCode();
                    ModifiedDuration = Integer.toString(Integer.parseInt(ModifiedDuration) + Integer.parseInt(lf.get(i).getDurationOfFlight()));
                    TotalCost += lf.get(i).getCost();
                    if (i != lf.size() - 1) {
                        from += "->";
                        ModifiedAirline += "->";
                        ModifiedCode+=" ";
                    }
                }
                Flight f = new Flight(ModifiedCode, ModifiedAirline, from, to, ModifiedDuration, lf.get(0).getStartTime(), lf.get(lf.size() - 1).getEndTime(), TotalCost, lf.get(0).getDateOfJourney());
                ModifiedListOfFlights.add(f);
            }
        }
        return ModifiedListOfFlights;
    }

    public ArrayList<Flight> getDirectFlights(String Date, String From, String To) {
        ArrayList<Flight> list = new ArrayList<>();
        if (graph.containsKey(From)) {
            for (Route e : graph.get(From)) {
                if (e.getTo().compareTo(To) == 0) {
                    for (Flight f:e.getListOfFlights()) {
                        if (f.getDateOfJourney().compareTo(Date)==0 && f.getSeatAvailable() > 0) {
                            list.add(f);
                        }
                    }
                }
            }
        }
        return list;
    }

    public Map<String, Destination> getMapOfVertices() {
        return MapOfVertices;
    }

    public Map<String, List<Route>> getGraph() {
        return graph;
    }


    public String getPrevTime() {
        return prevTime;
    }

    public void setVisited() {
        for (String s:MapOfVertices.keySet()) {
            isVisited.put(s, false);
            if (graph.containsKey(s)) {
                for (Route e:graph.get(s)) {
                    for (Flight f:e.getListOfFlights()) {
                        isVisitedFlight.put(f.getCode(), false);
                    }
                }
            }
        }
    }

    public void addNewEdge(Route route) {
        if (graph.containsKey(route.getFrom())) {
            int cnt = 0;
            for (Route e:graph.get(route.getFrom())) {
                if (e.getTo().compareTo(route.getTo())==0) {
                    return;
                }
                cnt++;
            }
            if (cnt==graph.get(route.getFrom()).size()) {
                graph.get(route.getFrom()).add(route);
            }
        } else {
            List<Route> temp = new ArrayList<>();
            temp.add(route);
            graph.put(route.getFrom(), temp);
        }
    }

    public void addNewFlight(Flight flight) {
        addNewEdge(new Route(flight.getFrom(), flight.getTo(), 500, null));
        for (Route e : graph.get(flight.getFrom())) {
            if (e.getTo().compareTo(flight.getTo()) == 0) {
                if (e.getListOfFlights() == null) {
                    ArrayList<Flight> temp = new ArrayList<>();
                    temp.add(flight);
                    e.setListOfFlights(temp);
                    break;
                } else {
                    e.getListOfFlights().add(flight);
                    break;
                }
            }
        }
    }

    public void addNewAirport(Destination airport) {
        MapOfVertices.put(airport.getName(), airport);
    }


    public void deleteAirport(Destination airport) {
        MapOfVertices.remove(airport.getName());
        if (graph.keySet().contains(airport.getName())) {
            graph.remove(airport.getName());
        }
        for (List<Route> le:graph.values()) {
            for (Route e:le) {
                if (e.getTo().compareTo(airport.getName())==0) {
                    le.remove(e);
                }
            }
        }
    }

    public void deleteFlight(Flight flight) {
        boolean flag = false;
        for (Route e:graph.get(flight.getFrom())) {
            for (Flight f:e.getListOfFlights()) {
                if (f.getCode().compareTo(flight.getCode())==0) {
                    e.getListOfFlights().remove(f);
                    flag = true;
                    break;
                }
            }
            if (flag) {
                break;
            }
        }
    }

    public int bookTicket(Flight flight, String DateOfJourney, int NumberOfPassengers) {
        if (flight.getCode().contains(" ")) {
            String words[]=flight.getCode().split(" ");
            for(String s: words){
                int flag = 0;
                for(List<Route>le:graph.values()) {
                    for (Route e:le) {
                        for (Flight f:e.getListOfFlights()) {
                            if (f.getCode().compareTo(s)==0) {
                                f.setSeatAvailable(f.getSeatAvailable() - NumberOfPassengers);
                                flag = 1;
                                break;
                            }
                        }
                        if (flag == 1) {
                            break;
                        }
                    }
                }
                if (flag == 1) {
                    break;
                }
            }
        } else {
            for (Route e : graph.get(flight.getFrom())) {
                for (Flight f : e.getListOfFlights()) {
                    if (f.getCode().compareTo(flight.getCode()) == 0) {
                        if (f.getSeatAvailable() - NumberOfPassengers > 0) {
                            f.setSeatAvailable(f.getSeatAvailable() - NumberOfPassengers);
                            return 1;
                        } else {
                            return -1;
                        }
                    }
                }
            }
        }
        return 1;
    }

    public void cancelTicket(Flight flight, String DateOfJourney, int NumberOfPassengers) {
        if (flight.getCode().contains(" ")) {
            String[] words = flight.getCode().split(" ");
            for(String s: words){
                int flag = 0;
                for(List<Route>le:graph.values()) {
                    for (Route e:le) {
                        for (Flight f:e.getListOfFlights()) {
                            if (f.getCode().compareTo(s)==0) {
                                f.setSeatAvailable(f.getSeatAvailable() + NumberOfPassengers);
                                flag = 1;
                                break;
                            }
                        }
                        if (flag == 1) {
                            break;
                        }
                    }
                }
                if (flag == 1) {
                    break;
                }
            }
        } else {
            int flag = 0;
            for (Route e : graph.get(flight.getFrom())) {
                for (Flight f : e.getListOfFlights()) {
                    if (f.getCode().compareTo(flight.getCode()) == 0) {
                        f.setSeatAvailable(f.getSeatAvailable() + NumberOfPassengers);
                        flag = 1;
                        break;
                    }
                }
                if (flag == 1) {
                    break;
                }
            }
        }
    }
    // add new flight(flight)
    // add new airport(vertex)
    // delete airport(vertex)
    // delete flight(flight)
    // book ticket - to reduce a seat by 1(flight, DOJ)
    // cancel ticket - +1
}
