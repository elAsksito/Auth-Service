package com.ask.auth.payload;

import lombok.Getter;

@Getter
public enum ErrorCodes {
        USER_NOT_FOUND(
                        "AUTH-001",
                        "https://docs.ask.com/errors/USR-001",
                        "Usuario No Encontrado",
                        "El usuario solicitado no existe en el sistema."),
        INVALID_PASSWORD(
                        "AUTH-002",
                        "https://docs.ask.com/errors/AUTH-002",
                        "Contraseña Inválida",
                        "La contraseña proporcionada es incorrecta."),
        ACCOUNT_LOCKED(
                        "AUTH-003",
                        "https://docs.ask.com/errors/AUTH-003",
                        "Cuenta Bloqueada",
                        "La cuenta de usuario ha sido bloqueada debido a múltiples intentos fallidos de inicio de sesión."),
        IP_BLOCKED(
                        "AUTH-004",
                        "https://docs.ask.com/errors/AUTH-004",
                        "IP Bloqueada",
                        "El acceso desde esta dirección IP ha sido bloqueado por razones de seguridad."),
        INVALID_JWT_TOKEN(
                        "AUTH-005",
                        "https://docs.ask.com/errors/AUTH-005",
                        "Token JWT Inválido",
                        "El token JWT proporcionado es inválido."),
        UNAUTHORIZED_ACCESS(
                        "AUTH-006",
                        "https://docs.ask.com/errors/AUTH-006",
                        "Acceso No Autorizado",
                        "No tienes permiso para acceder a este recurso."),
        TOO_MANY_LOGIN_ATTEMPTS(
                        "AUTH-007",
                        "https://docs.ask.com/errors/AUTH-007",
                        "Demasiados Intentos de Inicio de Sesión",
                        "Has superado el número máximo de intentos de inicio de sesión. Por favor, inténtalo más tarde."),
        USER_ALREADY_EXISTS(
                        "AUTH-008",
                        "https://docs.ask.com/errors/AUTH-008",
                        "El Usuario Ya Existe",
                        "Ya existe un usuario con este correo electrónico."),
        EMAIL_NOT_VERIFIED(
                        "AUTH-009",
                        "https://docs.ask.com/errors/AUTH-009",
                        "Correo No Verificado",
                        "Tu dirección de correo no ha sido verificada. Por favor, revisa tu bandeja de entrada."),
        INVALID_PASSWORD_RESET_TOKEN(
                        "AUTH-010",
                        "https://docs.ask.com/errors/AUTH-010",
                        "Token de Restablecimiento de Contraseña Inválido",
                        "El token para restablecer la contraseña es inválido o ha expirado."),
        SESSION_EXPIRED(
                        "AUTH-011",
                        "https://docs.ask.com/errors/AUTH-011",
                        "Sesión Expirada",
                        "Tu sesión ha expirado. Por favor, inicia sesión de nuevo."),
        USER_INACTIVE(
                        "AUTH-012",
                        "https://docs.ask.com/errors/AUTH-012",
                        "Usuario Inactivo",
                        "La cuenta de usuario está inactiva. Contacta con soporte para asistencia."),
        RESOURCE_ACCESS_DENIED(
                        "AUTH-013",
                        "https://docs.ask.com/errors/AUTH-013",
                        "Acceso Denegado al Recurso",
                        "El acceso al recurso solicitado está denegado."),
        ACCOUNT_EXPIRED(
                        "AUTH-014",
                        "https://docs.ask.com/errors/AUTH-014",
                        "Cuenta Expirada",
                        "La cuenta de usuario ha expirado. Por favor, contacta al administrador."),
        USER_CREATION_FAILED(
                        "AUTH-015",
                        "https://docs.ask.com/errors/AUTH-015",
                        "Error al Crear Usuario",
                        "Ocurrió un error al crear el usuario. Por favor, inténtalo nuevamente."),
        WEAK_PASSWORD(
                        "AUTH-016",
                        "https://docs.ask.com/errors/AUTH-016",
                        "Contraseña Débil",
                        "La contraseña no cumple con los requisitos mínimos de seguridad."),
        INVALID_CREDENTIALS(
                        "AUTH-017",
                        "https://docs.ask.com/errors/AUTH-017",
                        "Credenciales Inválidas",
                        "Las credenciales proporcionadas son incorrectas."),
        PASSWORD_ALREADY_USED(
                        "AUTH-018",
                        "https://docs.ask.com/errors/AUTH-018",
                        "Contraseña Ya Utilizada",
                        "No puedes reutilizar una contraseña que ya has usado anteriormente."),
        EXPIRED_JWT_TOKEN(
                        "AUTH-019",
                        "https://docs.ask.com/errors/AUTH-019",
                        "Token JWT Expirado",
                        "El token JWT ha expirado. Por favor, inicia sesión de nuevo."),
        NOT_AUTHENTICATED(
                        "AUTH-020",
                        "https://docs.ask.com/errors/AUTH-020",
                        "No Autenticado",
                        "No estás autenticado. Por favor, inicia sesión para continuar.");

        private final String code;
        private final String uri;
        private final String title;
        private final String message;

        ErrorCodes(String code, String uri, String title, String message) {
                this.code = code;
                this.uri = uri;
                this.title = title;
                this.message = message;
        }
}
