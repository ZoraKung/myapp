/**
 * Created by GHL on 2016/8/11.
 * Rev #1
 */
DROP VIEW [dbo].[view_test_form]
GO

SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE VIEW [dbo].[view_test_form] AS
  SELECT
    '001'              AS id,
    'ghl'              AS loginName,
    'GONGHL'           AS displayName,
    '835376597@qq.com' AS email
GO
