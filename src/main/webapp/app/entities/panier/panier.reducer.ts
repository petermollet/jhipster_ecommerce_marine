import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IPanier, defaultValue } from 'app/shared/model/panier.model';

export const ACTION_TYPES = {
  FETCH_PANIER_LIST: 'panier/FETCH_PANIER_LIST',
  FETCH_PANIER: 'panier/FETCH_PANIER',
  CREATE_PANIER: 'panier/CREATE_PANIER',
  UPDATE_PANIER: 'panier/UPDATE_PANIER',
  DELETE_PANIER: 'panier/DELETE_PANIER',
  RESET: 'panier/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IPanier>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type PanierState = Readonly<typeof initialState>;

// Reducer

export default (state: PanierState = initialState, action): PanierState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_PANIER_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PANIER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_PANIER):
    case REQUEST(ACTION_TYPES.UPDATE_PANIER):
    case REQUEST(ACTION_TYPES.DELETE_PANIER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_PANIER_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PANIER):
    case FAILURE(ACTION_TYPES.CREATE_PANIER):
    case FAILURE(ACTION_TYPES.UPDATE_PANIER):
    case FAILURE(ACTION_TYPES.DELETE_PANIER):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_PANIER_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_PANIER):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_PANIER):
    case SUCCESS(ACTION_TYPES.UPDATE_PANIER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_PANIER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/paniers';

// Actions

export const getEntities: ICrudGetAllAction<IPanier> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_PANIER_LIST,
  payload: axios.get<IPanier>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IPanier> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PANIER,
    payload: axios.get<IPanier>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IPanier> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PANIER,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IPanier> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PANIER,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IPanier> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PANIER,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
