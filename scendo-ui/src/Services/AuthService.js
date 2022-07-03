import axios from "axios";
const API_URL = "http://localhost:8080/api/";
class AuthService {
  login(utente) {
    return axios
      .post(API_URL + "authenticate", utente)
      .then(response => {
        if (response.data.accessToken) {
          localStorage.setItem("user", JSON.stringify(response.data));
        }
        return response.data;
      });
  }
  logout() {
    localStorage.removeItem("user");
  }
  register(utente) {
    return axios.post(API_URL + "registrazione", utente);
  }
  getCurrentUser() {
    return JSON.parse(localStorage.getItem('user'));;
  }
}
export default new AuthService();