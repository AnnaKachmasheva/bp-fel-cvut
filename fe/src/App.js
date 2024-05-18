import React from 'react'
import '../node_modules/bootstrap/dist/css/bootstrap.min.css'
import './App.scss'
import {Navigate, Route, Routes} from 'react-router-dom'
import {publicRoutes} from "./routes/PublicRoutes";
import BasicPage from "./pages/BasicPage";
import {PageTitles} from "./utils/Constants";
import ProductPage from "./pages/product-page/ProductPage";
import PrivateRoutes, {privateRoutes} from "./routes/PrivateRoutes";

function App() {

    return (
        <div className={"main-container"}>
            <Routes>
                <Route element={<PrivateRoutes/>}>
                    {privateRoutes.map(({path, main}) => (
                        <Route key={path}
                               path={path}
                               element={main()}>
                        </Route>
                    ))}

                    <Route key={"app/product/:id"}
                           path="app/product/:id"
                           element={<BasicPage page={<ProductPage/>}
                                               title={PageTitles.INVENTORY}/>}/>
                    <Route path="/" element={<Navigate replace to="app/dashboard"/>}/>
                </Route>
                {publicRoutes.map(({path, main}) => (
                    <Route key={path}
                           path={path}
                           element={main()}>
                    </Route>
                ))}
            </Routes>
        </div>
    )
}

export default App