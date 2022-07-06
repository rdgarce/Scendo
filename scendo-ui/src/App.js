import { BrowserRouter, Routes, Route } from 'react-router-dom';
import './App.css';
import Registrazione from './Components/Registrazione';
import Login from './Components/Login';
import Navbar from './Components/Navbar';
import Home from './Components/Home';
import Scendo from './Components/Scendo';
import CreaUscita from './Components/CreaUscita';

function App() {
  return (
    <>
    <BrowserRouter>
    <Navbar/>
      <Routes>
        <Route index element={<Scendo/>} />
        <Route path="/" element={<Scendo/>} />
        <Route path="/registrazione" element={<Registrazione/>}></Route>
        <Route path="/login" element={<Login/>}></Route>
        <Route path="/home" element={<Home/>}></Route>
        <Route path="/crea-uscita" element={<CreaUscita/>}></Route>
      </Routes>
    </BrowserRouter>
    </>
  );
}

export default App;
