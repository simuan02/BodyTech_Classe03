package bodyTech;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;


@WebServlet(name = "IndexServlet", urlPatterns = {"", "/IndexServlet"}, loadOnStartup = 1)
public class IndexServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*List<Prodotto> listaProdotti = ProdottoDAO.doRetrieveAll();

        List<Prodotto> prodottiMigliori = new ArrayList<>();
        List<String> immaginiProdotti;
        for (Prodotto p : listaProdotti){
            if (p.getId() < 10){
                immaginiProdotti = ImmaginiProdottiDAO.doRetrieveByIdProduct(p.getId());
                p.setImmagini(immaginiProdotti);
                prodottiMigliori.add(p);

            }
        }*/

        //request.setAttribute("venduti", prodottiMigliori);
        HttpSession session = request.getSession();
        if (session.isNew()){
            session.setAttribute("isLogged", false);
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/index.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
