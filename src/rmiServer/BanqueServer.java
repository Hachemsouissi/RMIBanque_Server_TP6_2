package rmiServer;

import rmiService.BanqueImpl;
import rmiService.IBanque;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.rmi.registry.LocateRegistry;
import java.util.Hashtable;
import java.util.Properties;

public class BanqueServer {
    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1099);
            System.out.println("RMI Registry démarré sur le port 1099.");

            IBanque banque = new BanqueImpl();
            Properties env = new Properties();
            env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.rmi.registry.RegistryContextFactory");
            env.put(Context.PROVIDER_URL, "rmi://localhost:1099");
            Context ctx = new InitialContext(env);

            try {
                ctx.rebind("IBanque", banque);
                System.out.println("Objet IBanque bindé via JNDI sous le nom 'IBanque'.");
            } catch (NamingException ne) {
                ctx.rebind("rmi://localhost:1099/IBanque", banque);
                System.out.println("Objet IBanque bindé via JNDI sous 'rmi://localhost:1099/IBanque'.");
            }

            System.out.println("Serveur RMI prêt.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
