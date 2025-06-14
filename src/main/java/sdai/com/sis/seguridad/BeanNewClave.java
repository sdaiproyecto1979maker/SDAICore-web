package sdai.com.sis.seguridad;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import sdai.com.sis.beans.IBeanDDatos;
import sdai.com.sis.dsentidades.IDSEntidad;
import sdai.com.sis.validaciones.GrupoNotBlank;
import sdai.com.sis.validaciones.GrupoSize;

/**
 * @date 29/05/2025
 * @author Sergio_M
 * @since 0.0.0.0-RELEASE
 */
@Named
@ViewScoped
public class BeanNewClave implements IBeanDDatos, Serializable {

    @NotBlank(message = "SEGUR00000", groups = GrupoNotBlank.class)
    @Size(min = 3, max = 45, message = "SEGUR00001", groups = GrupoSize.class)
    private String codigoDUsuario;
    @NotBlank(message = "SEGUR00002", groups = GrupoNotBlank.class)
    @Size(min = 3, message = "SEGUR00003", groups = GrupoSize.class)
    private String passwordDUsuario;
    @NotBlank(message = "SEGUR00006", groups = GrupoNotBlank.class)
    @Size(min = 3, message = "SEGUR00007", groups = GrupoSize.class)
    private String passwordRUsuario;

    @Override
    public void loadDSEntidad(IDSEntidad dsEntidad) {
        dsEntidad.addDato(KSeguridad.Usuarios.AtributosDEntidad.CODIGUSUAR, getCodigoDUsuario());
        dsEntidad.addDato(KSeguridad.SecretosDUsuario.AtributosDEntidad.PASSWUSUAR, getPasswordDUsuario());
        dsEntidad.addDato(KSeguridad.SecretosDUsuario.PASSRUSUAR, getPasswordRUsuario());
    }

    public String getCodigoDUsuario() {
        return codigoDUsuario;
    }

    public void setCodigoDUsuario(String codigoDUsuario) {
        this.codigoDUsuario = codigoDUsuario;
    }

    public String getPasswordDUsuario() {
        return passwordDUsuario;
    }

    public void setPasswordDUsuario(String passwordDUsuario) {
        this.passwordDUsuario = passwordDUsuario;
    }

    public String getPasswordRUsuario() {
        return passwordRUsuario;
    }

    public void setPasswordRUsuario(String passwordRUsuario) {
        this.passwordRUsuario = passwordRUsuario;
    }

}
