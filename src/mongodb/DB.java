package mongodb;

import java.util.*;

/**
 * Created by helomari on 04/12/15.
 */
public class DB {
    public static DB INSTANCE = new DB();
    private List<Collection> collections;

    private Query query;
    private Query currentQuery;
    private String currentVar;
    private Map<String, Collection> hashedCollections;
    private Map<String, Field> hashedFields;

    private DB() {

        collections = new ArrayList<>();
        Collection personnes = new Collection("Personne");
        Collection formations = new Collection("Formation");
        Collection ecoles = new Collection("Ecole");


        Field id = new PrimaryKey("_id", personnes);
        Field nom = new Field("Nom", personnes);
        Field prenom = new Field("Prenom", personnes);
        Field idFormation = new ForeignKey("IDFormation", personnes, formations);
        Field idEcole = new ForeignKey("IDEcole", personnes, ecoles);

        Field idE = new PrimaryKey("_id", ecoles);
        Field nomE = new Field("Nom", formations);

        personnes.add(id)
                .add(nom)
                .add(prenom)
                .add(idFormation)
                .add(idEcole);

        // to remove
        Field idF = new PrimaryKey("id", formations);
        Field nomF = new Field("nom", formations);
        formations.add(idF)
                .add(idEcole)
                .add(nomF);
        // \to remove

        ecoles.add(idE)
                .add(nomE);

        collections.add(personnes);
        collections.add(formations); // to remove
        collections.add(ecoles);
    }

    public void initQuery() {
        hashedCollections = new HashMap<>();
        hashedFields = new HashMap<>();
        query = null;
        Utils.INSTANCE.resetCursor();
    }

    public void newQuery(String document, List<String> tablesOrColumns) {
        currentQuery = new Query();
        List<Collection> cs = getCollectionsFromTablesOrColumns(document, tablesOrColumns);

        currentQuery.setCollection(cs.get(cs.size() - 1));

        currentQuery.createConditions(cs);
        if (query == null)
            query = currentQuery;
    }

    public void makeJoin() {
        query.addJoin(currentQuery);
    }

    private List<Collection> getCollectionsFromTablesOrColumns(String document, List<String> tablesOrColumns) {
        if (!document.equals(tablesOrColumns.get(0)))
            tablesOrColumns.add(0, document);
        System.out.println(tablesOrColumns);
        String last = tablesOrColumns.get(tablesOrColumns.size() - 1);
        System.out.println(last + " " + isField(last));
        List<Collection> res = new ArrayList<>();
        Field c = null;
        if (isField(last)) { // Last is an attribute
            System.out.println(tablesOrColumns.get(tablesOrColumns.size() - 2));
            Collection t = getCollection(tablesOrColumns.get(tablesOrColumns.size() - 2));
            if (t == null)
                throw new Error(tablesOrColumns.get(tablesOrColumns.size() - 2) + " is not a table.");
            if (!t.hasField(last)) {
                throw new Error("Last attribute is not in the table : " + tablesOrColumns.get(tablesOrColumns.size() - 2));
            }
            c = t.getField(last);
            tablesOrColumns.remove(tablesOrColumns.size() - 1);
        } else if (!isCollection(last)) { //Last is a tables
            throw new Error(last + " is neither a table nor a column!");
        }

        res.add(getCollection(tablesOrColumns.get(0)));
        for (int i = 1; i < tablesOrColumns.size(); i++) { // On cherche a voir si toute les tables sont liées
            Collection current = getCollection(tablesOrColumns.get(i));
            Collection previous = getCollection(tablesOrColumns.get(i - 1));
            if (previous.isLinkedTo(current)) { // si lié on rajoute la table current
                res.add(current);
            } else {
                throw new Error("The collections: " + current.getName() + " and " + previous.getName() + " are not linked");
            }
        }

        System.out.println("RES " + res);

        System.out.println(document + " " + currentVar);

        if (currentVar != null) {
            hashedCollections.put(currentVar, res.get(res.size() - 1));
            if (isField(last))
                hashedFields.put(currentVar, c);
        }

        System.out.println(hashedCollections);
        System.out.println(hashedFields);

        return res;
    }

    private boolean isCollection(String collection) {
        return getCollection(collection) != null;
    }

    private Collection getCollection(String c) {
        Optional<Collection> o = collections.stream()
                .filter(collection -> c.toLowerCase().equals(collection.getName().toLowerCase()))
                .findFirst();
        return o.isPresent() ? o.get() : null;
    }

    private boolean isField(String field) {
        return collections.stream()
                .anyMatch(collection -> collection.hasField(field));
    }


    public void setCurrentVar(String currentVar) {
        this.currentVar = currentVar;
    }

    public Query getQuery() {
        return query;
    }

//    public QueryBuilder makeCondition(QueryBuilder cmwhere1, String operator, QueryBuilder cmwhere2) {
//        if (operator.equals("and"))
//            return cmwhere1.and(cmwhere2.get());
//        return cmwhere1.or(cmwhere2.get());
//    }
}
