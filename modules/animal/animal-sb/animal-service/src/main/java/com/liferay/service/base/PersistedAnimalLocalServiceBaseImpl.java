/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.service.base;

import com.liferay.exportimport.kernel.lar.ExportImportHelperUtil;
import com.liferay.exportimport.kernel.lar.ManifestSummary;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.model.PersistedAnimal;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DefaultActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalServiceImpl;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.service.PersistedAnimalLocalService;
import com.liferay.service.persistence.PersistedAnimalPersistence;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Reference;

/**
 * Provides the base implementation for the persisted animal local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.service.impl.PersistedAnimalLocalServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.service.impl.PersistedAnimalLocalServiceImpl
 * @generated
 */
public abstract class PersistedAnimalLocalServiceBaseImpl
	extends BaseLocalServiceImpl
	implements AopService, IdentifiableOSGiService,
			   PersistedAnimalLocalService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Use <code>PersistedAnimalLocalService</code> via injection or a <code>org.osgi.util.tracker.ServiceTracker</code> or use <code>com.liferay.service.PersistedAnimalLocalServiceUtil</code>.
	 */

	/**
	 * Adds the persisted animal to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect PersistedAnimalLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param persistedAnimal the persisted animal
	 * @return the persisted animal that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public PersistedAnimal addPersistedAnimal(PersistedAnimal persistedAnimal) {
		persistedAnimal.setNew(true);

		return persistedAnimalPersistence.update(persistedAnimal);
	}

	/**
	 * Creates a new persisted animal with the primary key. Does not add the persisted animal to the database.
	 *
	 * @param animalId the primary key for the new persisted animal
	 * @return the new persisted animal
	 */
	@Override
	@Transactional(enabled = false)
	public PersistedAnimal createPersistedAnimal(long animalId) {
		return persistedAnimalPersistence.create(animalId);
	}

	/**
	 * Deletes the persisted animal with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect PersistedAnimalLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param animalId the primary key of the persisted animal
	 * @return the persisted animal that was removed
	 * @throws PortalException if a persisted animal with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public PersistedAnimal deletePersistedAnimal(long animalId)
		throws PortalException {

		return persistedAnimalPersistence.remove(animalId);
	}

	/**
	 * Deletes the persisted animal from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect PersistedAnimalLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param persistedAnimal the persisted animal
	 * @return the persisted animal that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public PersistedAnimal deletePersistedAnimal(
		PersistedAnimal persistedAnimal) {

		return persistedAnimalPersistence.remove(persistedAnimal);
	}

	@Override
	public DynamicQuery dynamicQuery() {
		Class<?> clazz = getClass();

		return DynamicQueryFactoryUtil.forClass(
			PersistedAnimal.class, clazz.getClassLoader());
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return persistedAnimalPersistence.findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.model.impl.PersistedAnimalModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return persistedAnimalPersistence.findWithDynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.model.impl.PersistedAnimalModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator) {

		return persistedAnimalPersistence.findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return persistedAnimalPersistence.countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		DynamicQuery dynamicQuery, Projection projection) {

		return persistedAnimalPersistence.countWithDynamicQuery(
			dynamicQuery, projection);
	}

	@Override
	public PersistedAnimal fetchPersistedAnimal(long animalId) {
		return persistedAnimalPersistence.fetchByPrimaryKey(animalId);
	}

	/**
	 * Returns the persisted animal matching the UUID and group.
	 *
	 * @param uuid the persisted animal's UUID
	 * @param groupId the primary key of the group
	 * @return the matching persisted animal, or <code>null</code> if a matching persisted animal could not be found
	 */
	@Override
	public PersistedAnimal fetchPersistedAnimalByUuidAndGroupId(
		String uuid, long groupId) {

		return persistedAnimalPersistence.fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the persisted animal with the primary key.
	 *
	 * @param animalId the primary key of the persisted animal
	 * @return the persisted animal
	 * @throws PortalException if a persisted animal with the primary key could not be found
	 */
	@Override
	public PersistedAnimal getPersistedAnimal(long animalId)
		throws PortalException {

		return persistedAnimalPersistence.findByPrimaryKey(animalId);
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery =
			new DefaultActionableDynamicQuery();

		actionableDynamicQuery.setBaseLocalService(persistedAnimalLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(PersistedAnimal.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName("animalId");

		return actionableDynamicQuery;
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			new IndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setBaseLocalService(
			persistedAnimalLocalService);
		indexableActionableDynamicQuery.setClassLoader(getClassLoader());
		indexableActionableDynamicQuery.setModelClass(PersistedAnimal.class);

		indexableActionableDynamicQuery.setPrimaryKeyPropertyName("animalId");

		return indexableActionableDynamicQuery;
	}

	protected void initActionableDynamicQuery(
		ActionableDynamicQuery actionableDynamicQuery) {

		actionableDynamicQuery.setBaseLocalService(persistedAnimalLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(PersistedAnimal.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName("animalId");
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		final PortletDataContext portletDataContext) {

		final ExportActionableDynamicQuery exportActionableDynamicQuery =
			new ExportActionableDynamicQuery() {

				@Override
				public long performCount() throws PortalException {
					ManifestSummary manifestSummary =
						portletDataContext.getManifestSummary();

					StagedModelType stagedModelType = getStagedModelType();

					long modelAdditionCount = super.performCount();

					manifestSummary.addModelAdditionCount(
						stagedModelType, modelAdditionCount);

					long modelDeletionCount =
						ExportImportHelperUtil.getModelDeletionCount(
							portletDataContext, stagedModelType);

					manifestSummary.addModelDeletionCount(
						stagedModelType, modelDeletionCount);

					return modelAdditionCount;
				}

			};

		initActionableDynamicQuery(exportActionableDynamicQuery);

		exportActionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					portletDataContext.addDateRangeCriteria(
						dynamicQuery, "modifiedDate");
				}

			});

		exportActionableDynamicQuery.setCompanyId(
			portletDataContext.getCompanyId());

		exportActionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<PersistedAnimal>() {

				@Override
				public void performAction(PersistedAnimal persistedAnimal)
					throws PortalException {

					StagedModelDataHandlerUtil.exportStagedModel(
						portletDataContext, persistedAnimal);
				}

			});
		exportActionableDynamicQuery.setStagedModelType(
			new StagedModelType(
				PortalUtil.getClassNameId(PersistedAnimal.class.getName())));

		return exportActionableDynamicQuery;
	}

	/**
	 * @throws PortalException
	 */
	public PersistedModel createPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return persistedAnimalPersistence.create(
			((Long)primaryKeyObj).longValue());
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {

		return persistedAnimalLocalService.deletePersistedAnimal(
			(PersistedAnimal)persistedModel);
	}

	public BasePersistence<PersistedAnimal> getBasePersistence() {
		return persistedAnimalPersistence;
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return persistedAnimalPersistence.findByPrimaryKey(primaryKeyObj);
	}

	/**
	 * Returns all the persisted animals matching the UUID and company.
	 *
	 * @param uuid the UUID of the persisted animals
	 * @param companyId the primary key of the company
	 * @return the matching persisted animals, or an empty list if no matches were found
	 */
	@Override
	public List<PersistedAnimal> getPersistedAnimalsByUuidAndCompanyId(
		String uuid, long companyId) {

		return persistedAnimalPersistence.findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of persisted animals matching the UUID and company.
	 *
	 * @param uuid the UUID of the persisted animals
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of persisted animals
	 * @param end the upper bound of the range of persisted animals (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching persisted animals, or an empty list if no matches were found
	 */
	@Override
	public List<PersistedAnimal> getPersistedAnimalsByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		OrderByComparator<PersistedAnimal> orderByComparator) {

		return persistedAnimalPersistence.findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the persisted animal matching the UUID and group.
	 *
	 * @param uuid the persisted animal's UUID
	 * @param groupId the primary key of the group
	 * @return the matching persisted animal
	 * @throws PortalException if a matching persisted animal could not be found
	 */
	@Override
	public PersistedAnimal getPersistedAnimalByUuidAndGroupId(
			String uuid, long groupId)
		throws PortalException {

		return persistedAnimalPersistence.findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns a range of all the persisted animals.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.model.impl.PersistedAnimalModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of persisted animals
	 * @param end the upper bound of the range of persisted animals (not inclusive)
	 * @return the range of persisted animals
	 */
	@Override
	public List<PersistedAnimal> getPersistedAnimals(int start, int end) {
		return persistedAnimalPersistence.findAll(start, end);
	}

	/**
	 * Returns the number of persisted animals.
	 *
	 * @return the number of persisted animals
	 */
	@Override
	public int getPersistedAnimalsCount() {
		return persistedAnimalPersistence.countAll();
	}

	/**
	 * Updates the persisted animal in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect PersistedAnimalLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param persistedAnimal the persisted animal
	 * @return the persisted animal that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public PersistedAnimal updatePersistedAnimal(
		PersistedAnimal persistedAnimal) {

		return persistedAnimalPersistence.update(persistedAnimal);
	}

	@Override
	public Class<?>[] getAopInterfaces() {
		return new Class<?>[] {
			PersistedAnimalLocalService.class, IdentifiableOSGiService.class,
			PersistedModelLocalService.class
		};
	}

	@Override
	public void setAopProxy(Object aopProxy) {
		persistedAnimalLocalService = (PersistedAnimalLocalService)aopProxy;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return PersistedAnimalLocalService.class.getName();
	}

	protected Class<?> getModelClass() {
		return PersistedAnimal.class;
	}

	protected String getModelClassName() {
		return PersistedAnimal.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource = persistedAnimalPersistence.getDataSource();

			DB db = DBManagerUtil.getDB();

			sql = db.buildSQL(sql);
			sql = PortalUtil.transformSQL(sql);

			SqlUpdate sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(
				dataSource, sql);

			sqlUpdate.update();
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
	}

	protected PersistedAnimalLocalService persistedAnimalLocalService;

	@Reference
	protected PersistedAnimalPersistence persistedAnimalPersistence;

	@Reference
	protected com.liferay.counter.kernel.service.CounterLocalService
		counterLocalService;

	@Reference
	protected com.liferay.portal.kernel.service.ClassNameLocalService
		classNameLocalService;

	@Reference
	protected com.liferay.portal.kernel.service.ResourceLocalService
		resourceLocalService;

	@Reference
	protected com.liferay.portal.kernel.service.UserLocalService
		userLocalService;

	@Reference
	protected com.liferay.asset.kernel.service.AssetEntryLocalService
		assetEntryLocalService;

	@Reference
	protected com.liferay.asset.kernel.service.AssetTagLocalService
		assetTagLocalService;

}