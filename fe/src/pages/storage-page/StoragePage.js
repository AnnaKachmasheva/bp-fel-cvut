import React, {Component, useEffect, useState} from "react";
import Pagination from "../../components/pagination/Pagination";
import Button, {ButtonSize, ButtonType} from "../../components/button/Button";
import {formatDatetime, showStatus, showValue} from "../../utils/Common";
import {FiPlus} from "react-icons/fi";
import {FaArrowDown, FaArrowUp} from "react-icons/fa";
import styles from "../storage-page/StoragePage.module.scss";
import {removeToken} from "../../services/auth";
import {useNavigate} from "react-router-dom";
import {ModalAddProduct} from "./modal-add-product/ModalAddProduct";
import {userApi} from "../../services/api";
import {IoIosMore, IoMdClose} from "react-icons/io";
import {CiImageOff} from "react-icons/ci";
import {ModalDeleteProductConfirm} from "./modal-delete-product/ModalDeleteProductConfirm";
import {ModalUpdateProduct} from "./modal-update-product/ModalUpdateProduct";

function StoragePage() {

    const navigate = useNavigate();

    const [listProducts, setListProducts] = useState([]);
    const [currentPage, setCurrentPage] = useState(1);
    const [totalPages, setTotalPages] = useState(0);
    const [totalElements, setTotalElements] = useState(0);
    const [countElements, setCountElements] = useState(5);
    const [showModalAddProduct, setShowModalAddProduct] = useState(false);
    const [isDropdownVisible, setDropdownVisible] = useState(false);
    const sortOptions = [
        {key: 'name', option: 'Name'},
        {key: 'updatedAt', option: 'Last updated'},
    ]
    const [selectedOptionSort, setSelectedOptionSort] = useState(sortOptions[0].key);
    const [isUp, setIsUp] = useState(false);
    const [categories, setCategories] = useState([]);

    // add product window
    const [selectedPhotos, setSelectedPhotos] = useState([]);
    const [isDropdownSelectCategoryVisible, setDropdownSelectCategoryVisible] = useState(false);
    const [selectedCategories, setSelectedCategories] = useState([]);
    const [selectAllCategories, setSelectAllCategories] = useState(true);

    const [statusesFetched, setStatusesFetched] = useState(false);
    const [statuses, setStatuses] = useState([]);


    useEffect(() => {
        fetchCategories().then(() => {
            fetchStatuses().then(() => {
                setStatusesFetched(true);
            });
        });
    }, []);

    useEffect(() => {
        if (statusesFetched) {
            fetchProducts().then(() => {
            });
        }
    }, [statusesFetched, selectAllCategories, selectedCategories, selectedOptionSort, isUp, totalPages, countElements, currentPage]);

    const handlePageChange = (pageNumber) => {
        if (pageNumber === currentPage)
            return;
        setCurrentPage(pageNumber);
    };

    const handleChangeCountElements = (count) => {
        setCountElements(count);
    }

    const sortList = (key) => {
        const selectedOption = sortOptions.find(option => option.key === key);
        if (selectedOption.key === selectedOptionSort) {
            setIsUp(!isUp);
        } else {
            setIsUp(false);
            setSelectedOptionSort(selectedOption.key);
        }
    }

    function getOptionByKey(key) {
        for (let i = 0; i < sortOptions?.length; i++) {
            if (sortOptions[i].key === key) {
                return sortOptions[i].option;
            }
        }
        return null;
    }

    const handlePhotoChange = (newFiles) => {
        setSelectedPhotos([...selectedPhotos, ...newFiles]);
    }
    const handlePhotoSet = (newFiles) => {
        setSelectedPhotos(newFiles);
    }

    const handleSelectAllCategories = () => {
        if (!selectAllCategories) {
            setSelectedCategories(categories);
        }
        setSelectAllCategories(!selectAllCategories);
    }

    const handleIsCheckedCategory = (name) => {
        for (let i = 0; i < selectedCategories?.length; i++) {
            if (selectedCategories[i].name === name) {
                return true;
            }
        }
        return false;
    }

    const handleSelectCategory = (category) => {
        console.log('handleSelectCategory ' + category)

        const isAlreadySelected = selectedCategories.includes(category);

        console.log('isAlreadySelected ' + isAlreadySelected)

        if (isAlreadySelected) {
            setSelectedCategories(selectedCategories.filter(cat => cat.name !== category.name));
        } else {
            setSelectedCategories([...selectedCategories, category]);
        }
    }

    const handleRemoveSelectedCategory = (category) => {
        setSelectedCategories(selectedCategories.filter(cat => cat.name !== category.name));
    }

    const fetchCategories = async () => {
        try {
            const categoriesData = await userApi.getAllCategories();
            setCategories(categoriesData.data);
            setSelectedCategories(categoriesData.data);

            console.log('categories ' + JSON.stringify(categoriesData.data))

        } catch (error) {
            removeToken();
            navigate('/app/logout');
        }
    };

    const fetchProducts = async () => {
        try {
            const sort = selectedOptionSort;
            const order = isUp ? 'ASC' : 'DESC';

            const products = await userApi.getAllProducts(selectedCategories, currentPage, countElements, sort, order);

            setListProducts(products.data.content);
            setTotalPages(products.data.totalPages);
            setTotalElements(products.data.totalElements);
        } catch (error) {
            removeToken();
            navigate('/app/logout');
        }
    };

    const fetchStatuses = async () => {
        try {
            const statuses = await userApi.getAllStatuses();
            setStatuses(statuses.data);
        } catch (error) {
            removeToken();
            navigate('/app/logout');
        }
    };

    const goToProductPage = (product) => {
        const id = product.id;
        navigate(`/app/storage/product/${id}`, {state: {product: product, categories: categories, statuses: statuses}});
    }


    return (
        <div className={'content'}>

            <ModalAddProduct onClose={() => {
                setShowModalAddProduct(false)
                handlePhotoSet([])
            }}
                             show={showModalAddProduct}
                             categories={categories}
                             statuses={statuses}
                             onPhotoChange={handlePhotoChange}
                             photos={selectedPhotos}
                             onPhotoSet={handlePhotoSet}/>

            <div className={'panel'}>
                <div className={'total'}>
                    <Button onClick={() => setShowModalAddProduct(true)}
                            type={ButtonType[2].type}
                            size={ButtonSize[1].size}
                            isIconEnd={true}
                            icon={<FiPlus/>}
                            label={'Add product'}/>

                    <div className={styles.sortContainer}>
                        {/* button select sorting */}
                        <Button onClick={() => setDropdownVisible(!isDropdownVisible)}
                                type={ButtonType[3].type}
                                size={ButtonSize[1].size}
                                isIconEnd={true}
                                icon={isUp ? <FaArrowUp/> : <FaArrowDown/>}
                                label={getOptionByKey(selectedOptionSort)}/>

                        {/* sort */}
                        {isDropdownVisible &&
                            <div>
                                <div className={'container-list-options'}
                                     onClick={() => setDropdownVisible(false)}/>

                                <ul className={styles.selectCategories}>
                                    {sortOptions.map((option, index) => (
                                        <li key={index}
                                            onClick={() => sortList(option.key)}
                                            className={selectedOptionSort === option.option ? 'selected-option' : null}>
                                            {option.option}
                                            {selectedOptionSort === option.option && isUp && <FaArrowUp/>}
                                            {selectedOptionSort === option.option && !isUp && <FaArrowDown/>}
                                        </li>
                                    ))}
                                </ul>
                            </div>
                        }

                        <h4>Products: <span>{totalElements}</span></h4>
                    </div>
                </div>

                <div className={styles.selectCategoriesWrapper}>
                    <Button onClick={() => setDropdownSelectCategoryVisible(!isDropdownSelectCategoryVisible)}
                            type={ButtonType[3].type}
                            size={ButtonSize[1].size}
                            isIconEnd={true}
                            icon={isDropdownSelectCategoryVisible ? <FaArrowUp/> : <FaArrowDown/>}
                            label={'Select category'}/>

                    {isDropdownSelectCategoryVisible &&
                        <div className={styles.optionsWrapper}>
                            <div className={'container-list-options'}
                                 onClick={() => setDropdownSelectCategoryVisible(false)}/>

                            <ul className={styles.selectCategories}>
                                <li key={0}>
                                    <input type={"checkbox"}
                                           checked={selectAllCategories}
                                           onChange={handleSelectAllCategories}/>
                                    Select All
                                </li>
                                {categories.map((category, index) => (
                                    <li key={index + 1}>
                                        <input
                                            type="checkbox"
                                            checked={handleIsCheckedCategory(category.name)}
                                            onChange={() => handleSelectCategory(category)}/>
                                        {category.name}
                                    </li>
                                ))}
                            </ul>
                        </div>
                    }
                    <ul className={styles.categories}>
                        {selectedCategories.map((category, index) =>
                            <li key={index}
                                onClick={() => handleRemoveSelectedCategory(category)}>
                                {category.name} <IoMdClose/>
                            </li>
                        )}
                    </ul>
                </div>


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
                        <td>
                            DATE
                        </td>
                        <td className={styles.actions}>
                        </td>
                    </tr>
                    </thead>

                    <tbody>
                    {listProducts.map((product, index) =>
                        <TableRow product={product}
                                  key={index}
                                  categories={categories}
                                  statuses={statuses}
                                  handleClick={() => goToProductPage(product)}
                        />
                    )}
                    </tbody>
                </table>
            </div>

            <Pagination currentPage={currentPage}
                        totalPages={totalPages}
                        onPageChange={handlePageChange}
                        countElements={countElements}
                        changeCountElemnts={handleChangeCountElements}
                        name={'products'}/>

        </div>
    )
}

class TableRow extends Component {

    constructor(props) {
        super(props);
        this.state = {
            showConfirmDeleteProduct: false,
            showUpdateProduct: false,
            showOptions: false
        };
    }

    handleShowOptions = () => {
        this.setState(prevState => ({
            showOptions: !prevState.showOptions
        }));
    }

    toggleDeleteModal = () => {
        this.setState(prevState => ({
            showConfirmDeleteProduct: !prevState.showConfirmDeleteProduct
        }));
    };

    toggleEditModal = () => {
        this.setState(prevState => ({
            showUpdateProduct: !prevState.showUpdateProduct
        }));
    };

    render() {
        const {showConfirmDeleteProduct} = this.state;
        const {showUpdateProduct} = this.state;
        const {showOptions} = this.state;

        return (
            <tr className={this.props.product?.isDeleted ? 'deleted-item' : ''}>
                <ModalDeleteProductConfirm onClose={() => this.setState({showConfirmDeleteProduct: false})}
                                           show={showConfirmDeleteProduct}
                                           product={this.props.product}/>

                <ModalUpdateProduct onClose={() => this.setState({showUpdateProduct: false})}
                                    show={showUpdateProduct}
                                    product={this.props.product}
                                    categories={this.props.categories}
                                    statuses={this.props.statuses}/>

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
                <td onClick={this.props.handleClick}>{formatDatetime(this.props.product?.updatedAt)}</td>
                <td className={styles.actions}
                    onClick={this.handleShowOptions}>
                    {showOptions ? <IoMdClose/> : <IoIosMore/>}
                    <ul className={showOptions ? styles.showActions : styles.hiddenActions}>
                        {this.props.product?.isDeleted ? null : <li onClick={this.toggleDeleteModal}>Delete</li>}
                        <li onClick={this.toggleEditModal}>Edit</li>
                    </ul>
                </td>
            </tr>
        )
    }
}

export default StoragePage;