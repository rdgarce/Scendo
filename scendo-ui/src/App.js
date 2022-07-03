import { BrowserRouter, Routes, Route } from 'react-router-dom';
import './App.css';
import Registrazione from './Components/Registrazione';
import Login from './Components/Login';

function App() {
  return (
    <>
    <BrowserRouter>
      <Routes>
        <Route index element={<Registrazione/>} />
        <Route path="/" element={<Registrazione/>} />
        <Route path="/registrazione" element={<Registrazione/>}></Route>
        <Route path="/login" element={<Login/>}></Route>
      </Routes>
    </BrowserRouter>
    </>
  );
}

export default App;
