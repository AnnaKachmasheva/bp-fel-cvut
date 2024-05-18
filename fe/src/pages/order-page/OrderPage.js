import React, {Component, useEffect, useState} from "react"
import Button, {ButtonSize, ButtonType} from "../../components/button/Button";
import {useNavigate, useParams} from "react-router-dom";
import {isAdmin, isUser} from "../../services/auth";
import styles from "../storage-page/StoragePage.module.scss";
import {CiImageOff} from "react-icons/ci";
import {showStatus, showValue} from "../../utils/Common";
import {FaCheck, FaPen} from "react-icons/fa";
import {MdChecklistRtl, MdDeleteOutline, MdOutlineCancel} from "react-icons/md";
import {RiDeleteBinLine, RiListCheck3} from "react-icons/ri";
import {userApi} from "../../services/api";
import {BsQuestionCircle} from "react-icons/bs";
import {GiBackwardTime} from "react-icons/gi";
import {ModalDeleteOrderConfirm} from "./modal-delete-order/ModalDeleteOrderConfirm";
import {ModalUpdateOrder} from "../orders-page/modal-update-order/ModalUpdateOrder";
import {ModalScanCode} from "./modal-scan-code/ModalScanCode";
import {HiOutlinePencilAlt} from "react-icons/hi";


function OrderPage() {

    const navigate = useNavigate();
    const admin = isAdmin();
    const [updatedOrder, setUpdatedOrder] = useState(null);
    const [errorFromServer, setErrorFromServer] = useState("");
    const [helpVisible, setHelpVisible] = useState(false);
    const [order, setOrder] = useState(null);
    const [orderStatus, setOrderStatus] = useState(" ");
    const [products, setProducts] = useState([]);
    const [isDeleted, setIsDeleted] = useState(false);
    const {id} = useParams();
    const [showConfirmDelete, setShowConfirmDelete] = useState(false);
    const [showUpdate, setShowUpdate] = useState(false);

    const [successScanProducts, setSuccessScanProducts] = useState([]);
    const [startScan, setStartScan] = useState(false);
    const [showScanModal, setShowScanModal] = useState(false);
    const [scannedCode, setScannedCode] = useState("");


    useEffect(() => {
        fetchOrder().then();
    }, []);

    const fetchOrder = async () => {
        try {
            const response = await userApi.getOrderById(id);
            if (response && response.error) {
                setErrorFromServer(response.error.message);
            } else {
                setOrder(response.data.order);
                setOrderStatus(response.data?.status.name || "");
                setProducts(response.data?.product || []);
                setIsDeleted(response.data?.isDeleted || false)
            }
        } catch (error) {
            navigate(-1);
        }
    };

    const updateStatus = async (newStatus) => {
        try {
            const orderId = id;
            const status = {
                "name": newStatus
            };

            const response = await userApi.updateStatusOrder(orderId, status);

            if (response && response.error) {
                setErrorFromServer(response.error.message);
            } else {
                setUpdatedOrder(response.data.content);
                window.location.reload();
            }

        } catch (error) {
            setErrorFromServer("Error updating status");
        }
    }

    const handleAccept = (order) => {
        setStartScan(true);
    };

    const goToProductPage = (product) => {
        const id = product.id;
        navigate(`/app/storage/product/${id}`, {state: {product: product}});
    };

    const deleteOrder = () => {

    };
    const updateOrder = () => {
        //todo
    };

    const completeWithoutMissingItems = () => {
        // Logic to complete without missing items
    };

    const changeStatus = (status) => {
        updateStatus(status).then();
    }


    return (
        <div className={'content'}>

            <ModalDeleteOrderConfirm onClose={() => setShowConfirmDelete(false)}
                                     show={showConfirmDelete}
                                     orderId={id}/>

            <ModalUpdateOrder onClose={() => setShowUpdate(false)}
                              show={showUpdate}
                              products={products}
                              updateOrder={() => updateOrder()}/>

            <ModalScanCode onClose={() => setShowScanModal(false)}
                           show={showScanModal}
                           scanedCode={setScannedCode}/>

            <h3>{id}</h3>

            <span className={styles.error}>{errorFromServer}</span>

            <h1 className={'form-title '}>
                <span className={orderStatus.toLowerCase()
                    .concat(" ")
                    .concat(styles.status)}>
                    {orderStatus.toLowerCase()}
                </span>
            </h1>

            <h5>Products</h5>
            <table>
                <thead>
                <tr>
                    <td className={styles.imageColumn}>
                        IMAGE
                    </td>
                    <td>
                        CATEGORY
                    </td>
                    <td>
                        NAME
                    </td>
                    <td>
                        STATE
                    </td>
                    <td>
                        DESCRIPTION
                    </td>
                </tr>
                </thead>

                <tbody>
                {products.map((product, index) =>
                    <TableRow product={product}
                              key={index}
                              startScan={startScan}
                              handleClick={!startScan ? () => goToProductPage(product) :
                                  () => setShowScanModal(true)}
                    />
                )}
                </tbody>
            </table>


            <div className={styles.buttons}>

                <span>{errorFromServer.message}</span>

                {!admin && orderStatus === 'CREATED' ?
                    <Button onClick={() => changeStatus("PROCESSING")}
                            type={ButtonType[2].type}
                            size={ButtonSize[1].size}
                            isIconEnd={true}
                            icon={<FaCheck/>}
                            label={'Start order'}/>
                    : null
                }

                {admin && orderStatus === 'CREATED' ?
                    <Button onClick={() => setShowUpdate(true)}
                            type={ButtonType[2].type}
                            size={ButtonSize[1].size}
                            isIconEnd={true}
                            icon={<FaPen/>}
                            label={'Update'}/>
                    : null
                }

                {admin && orderStatus === 'CREATED' && !isDeleted ?
                    <Button onClick={() => setShowConfirmDelete(true)}
                            type={ButtonType[4].type}
                            size={ButtonSize[1].size}
                            isIconEnd={true}
                            icon={<MdDeleteOutline/>}
                            label={'Delete'}/>
                    : null
                }

                {!admin && orderStatus === 'PROCESSING' ?
                    <Button onClick={() => changeStatus("COMPLETE")}
                            type={ButtonType[2].type}
                            size={ButtonSize[1].size}
                            isIconEnd={true}
                            icon={<MdChecklistRtl/>}
                            label={'Complete'}/>
                    : null
                }

                {!admin && orderStatus === 'PROCESSING' ?
                    <Button onClick={() => changeStatus("BACKORDERED")}
                            type={ButtonType[0].type}
                            size={ButtonSize[1].size}
                            isIconEnd={true}
                            icon={<GiBackwardTime/>}
                            label={'Back ordering'}/>
                    : null
                }

                {admin && orderStatus === 'BACKORDERED' ?
                    <Button onClick={() => changeStatus("CANCELED")}
                            type={ButtonType[4].type}
                            size={ButtonSize[1].size}
                            isIconEnd={true}
                            icon={<MdOutlineCancel/>}
                            label={'Cancel'}/>
                    : null
                }

                {admin && orderStatus === 'BACKORDERED' ?
                    <div className={styles.completeButtinContainer}>
                        <Button onClick={completeWithoutMissingItems}
                                type={ButtonType[2].type}
                                size={ButtonSize[1].size}
                                isIconEnd={true}
                                icon={<RiListCheck3/>}
                                label={'Submit'}/>
                        <div
                            onMouseEnter={() => setHelpVisible(true)}
                            onMouseLeave={() => setHelpVisible(false)}>
                            <BsQuestionCircle size={24}
                                              className={styles.iconHelp}/>
                        </div>
                        <p className={`${styles.helpMessage} ${helpVisible ? styles.visible : styles.notVisible}`}>
                            Submit without missing items
                        </p>
                    </div>
                    : null
                }

            </div>

        </div>
    )
}

class TableRow extends Component {

    render() {
        return (
            <tr className={this.props.product?.isDeleted ? 'deleted-item' : ''}>
                <td className={styles.imageColumn}
                    onClick={this.props.handleClick}>
                    {this.props.product.images?.length > 0 ?
                        <img src={this.props.product?.images[0]} alt="First product image"/> :
                        <CiImageOff className={styles.noImage}
                                    size={32}/>
                    }
                </td>
                <td onClick={this.props.handleClick}>{showValue(this.props.product?.category?.name)}</td>
                <td onClick={this.props.handleClick}>{showValue(this.props.product?.name)}</td>
                <td onClick={this.props.handleClick}>
                    {this.props.product.status ?
                        <span className={this.props.product.status.name.toLowerCase()
                            .concat(" ")
                            .concat(styles.status)}>
                         {showStatus(this.props.product.status.name)}
                    </span> : "undefined"
                    }
                </td>
                <td onClick={this.props.handleClick}>{showValue(this.props.product?.description)}</td>
                {(isUser() && (this.props.order?.status?.name === "PROCESSING")) ?
                    <td>
                        <div className={'buttons-container'}>
                            <Button type={ButtonType[3].type}
                                    size={ButtonSize[0].size}
                                    label={'Edit'}
                                    onClick={this.handleUpdate}
                                    isIconStart={true}
                                    icon={<HiOutlinePencilAlt/>}/>

                            {this.props.user.isDeleted ? null
                                : <Button type={ButtonType[5].type}
                                          size={ButtonSize[0].size}
                                          label={'Delete'}
                                          onClick={this.handleDelete}
                                          isIconStart={true}
                                          icon={<RiDeleteBinLine/>}/>
                            }
                        </div>


                    </td>
                    : null}
            </tr>
        )
    }
}

export default OrderPage;