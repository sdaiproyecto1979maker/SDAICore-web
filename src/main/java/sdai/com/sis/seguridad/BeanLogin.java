package sdai.com.sis.seguridad;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import sdai.com.sis.procesosdsesion.KProcesosDSesion;
import sdai.com.sis.sistema.BeanGestor;

/**
 * @date 29/05/2025
 * @author Sergio_M
 * @since VERSIONDCOREENCURSO
 */
@Named
@ViewScoped
public class BeanLogin extends BeanGestor {

    public String login() {
        return procesarAccion(KProcesosDSesion.AccionesDSistema.ACCIOLOGIN);
    }

    public String newClave() {
        return procesarAccion(KProcesosDSesion.AccionesDSistema.ACNEWSECRE);
    }

}
