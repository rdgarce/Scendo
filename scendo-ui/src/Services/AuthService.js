import axios from "axios";
const API_URL = "http://18.212.181.68:8080/api/";
class AuthService {
  login(utente) {
    return axios
      .post(API_URL + "login", utente)
      .then(response => {
        if (response.data.message) {
          localStorage.setItem('token', JSON.stringify(response.data.message));
          localStorage.setItem('email_utente', utente.username);
          console.log("Ho salvato token ");
        }
        return response.data;
      });
  }
  logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('email_utente');
  }
  register(utente) {
    return axios.post(API_URL + "registrazione", utente);
  }
  getCurrentUser() {
    return JSON.parse(localStorage.getItem('token'));;
  }
}
export default new AuthService();