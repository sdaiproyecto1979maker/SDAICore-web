package sdai.com.sis.sistema;

import jakarta.annotation.PostConstruct;
import jakarta.faces.model.SelectItem;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import sdai.com.sis.cfg.ISdaiCFG;
import sdai.com.sis.idiomas.IIdioma;
import sdai.com.sis.idiomas.IIdiomas;
import sdai.com.sis.procesosdsesion.IGestorDProcesos;
import sdai.com.sis.sesionesdusuario.ISesionRegistrada;

/**
 * @date 30/05/2025
 * @author Sergio_M
 * @since 0.0.0.0-RELEASE
 */
@Named
@ViewScoped
public class BeanPaginas implements Serializable {

    @Inject
    private IGestorDProcesos gestorDProcesos;
    @Inject
    private IIdiomas idiomas;
    private String codigoDIdioma;
    @Inject
    private ISesionRegistrada sesionRegistrada;
    @Inject
    private ISdaiCFG sdaiCFG;

    @PostConstruct
    public void init() {
        IIdioma idioma = this.sesionRegistrada.getIdiomaDSesion();
        if (idioma == null) {
            idioma = this.idiomas.getIdiomaDefault();
        }
        this.codigoDIdioma = idioma.getCodigoDIdioma();
    }

    public List<SelectItem> getListaDIdiomas() {
        List<SelectItem> lista = new ArrayList<>();
        IIdioma[] _idiomas = this.idiomas.getIdiomasDSistema();
        for (IIdioma idioma : _idiomas) {
            lista.add(idioma.createSelectItem());
        }
        return lista;
    }

    public void cargarIdioma() {
        String codigo = getCodigoDIdioma();
        IIdioma idioma = this.idiomas.getIdioma(codigo);
        this.sesionRegistrada.setIdiomaDSesion(idioma);
    }

    public String getVersionDCore() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Versión Core: ");
        stringBuilder.append(this.sdaiCFG.getVersionDCore().getVersion());
        return stringBuilder.toString();
    }

    public String getVersionDFramework() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Versión Framework: ");
        stringBuilder.append(this.sdaiCFG.getVersionDFramework().getVersion());
        return stringBuilder.toString();
    }

    public String getVersionCustom() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Versión Custom: ");
        stringBuilder.append(this.sdaiCFG.getVersionCustom().getVersion());
        return stringBuilder.toString();
    }

    public boolean isMostrarListaDIdiomas() {
        return this.idiomas.getIdiomasDSistema().length > 1;
    }

    public void loadGestor() {
        this.gestorDProcesos.iniciar();
    }

    public String getPagina() {
        return this.gestorDProcesos.getPaginaDProceso();
    }

    public String getCodigoDIdioma() {
        return codigoDIdioma;
    }

    public void setCodigoDIdioma(String codigoDIdioma) {
        this.codigoDIdioma = codigoDIdioma;
    }

}
