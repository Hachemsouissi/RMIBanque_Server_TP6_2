package rmiService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import metier.Compte;

public class BanqueImpl extends UnicastRemoteObject implements IBanque {
    private static final long serialVersionUID = 1L;
    private Map<Integer, Compte> comptes;

    public BanqueImpl() throws RemoteException {
        super();
        comptes = new ConcurrentHashMap<>();
    }

    @Override
    public synchronized String creerCompte(Compte c) throws RemoteException {
        if (c == null) return "Compte invalide";
        int code = c.getCode();
        if (comptes.containsKey(code)) {
            return "Erreur : le code " + code + " existe déjà.";
        } else {
            comptes.put(code, c);
            return "Compte créé : " + c.toString();
        }
    }

    @Override
    public String getInfoCompte(int code) throws RemoteException {
        Compte c = comptes.get(code);
        if (c == null) return "Aucun compte avec le code " + code;
        return c.toString();
    }
}
